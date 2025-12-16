create or replace PROCEDURE PRC_SETTLEMENT_MANAGE (
    I_USER_ID    IN SETTLEMENT_REQUESTS.FROM_USER%TYPE,--I_FROM_USER
    I_GROUP_ID        IN SETTLEMENT_REQUESTS.GROUP_ID%TYPE,
    I_MEMBER_ID      IN SETTLEMENT_REQUESTS.TO_USER%TYPE,--I_TO_USER
    I_AMOUNT          IN SETTLEMENT_REQUESTS.AMOUNT%TYPE,
--    I_STATUS_CODE       IN SETTLEMENT_REQUESTS.STATUS_CODE%TYPE DEFAULT 1,
    I_OPERATION_STATE IN VARCHAR2,
    I_REQUEST_ID   IN SETTLEMENT_REQUESTS.REQUEST_ID%TYPE DEFAULT 0,
    O_STATUS_CODE     OUT NUMBER,
    O_STATUS_MESSAGE  OUT VARCHAR2
)
AS
v_count NUMBER;
v_req_id NUMBER;
BEGIN
    IF I_OPERATION_STATE = 'CREATE' THEN
        
        SELECT COUNT(1) INTO v_count FROM SETTLEMENT_REQUESTS WHERE GROUP_ID = I_GROUP_ID AND FROM_USER = I_USER_ID AND TO_USER = I_MEMBER_ID AND STATUS_CODE = 1003;
        
        IF v_count = 0 THEN
            INSERT INTO SETTLEMENT_REQUESTS (
                GROUP_ID, FROM_USER, TO_USER, AMOUNT
            ) VALUES (
                I_GROUP_ID, I_USER_ID, I_MEMBER_ID, I_AMOUNT--, NVL(I_STATUS_CODE, 1)
            );
            
            UPDATE SPLITS SET STATUS_CODE = 1000 WHERE EXPENSE_ID IN (select expense_id FROM EXPENSES
                WHERE GROUP_ID = I_GROUP_ID AND PAID_BY = I_MEMBER_ID)
                AND USER_ID = I_USER_ID;
        ELSE
            SELECT REQUEST_ID INTO v_req_id FROM SETTLEMENT_REQUESTS WHERE GROUP_ID = I_GROUP_ID AND FROM_USER = I_USER_ID AND TO_USER = I_MEMBER_ID AND STATUS_CODE = 1003;
            
            UPDATE SETTLEMENT_REQUESTS SET STATUS_CODE = 1000, AMOUNT = I_AMOUNT, REQUESTED_ON = CURRENT_TIMESTAMP WHERE REQUEST_ID = v_req_id;
            
            UPDATE SPLITS SET STATUS_CODE = 1000 WHERE EXPENSE_ID IN (select expense_id FROM EXPENSES
                WHERE GROUP_ID = I_GROUP_ID AND PAID_BY = I_MEMBER_ID)
                AND USER_ID = I_USER_ID;
        END IF;

        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'Settlement requested successfully.';

    ELSIF I_OPERATION_STATE = 'APPROVE' THEN
        UPDATE SETTLEMENT_REQUESTS
           SET STATUS_CODE = 1001, MODIFIED_ON = CURRENT_TIMESTAMP
         WHERE REQUEST_ID = I_REQUEST_ID;
         
         UPDATE SPLITS SET STATUS_CODE = 1001 where expense_id in (select expense_id from expenses where group_id = I_GROUP_ID AND PAID_BY = I_USER_ID)
        AND USER_ID = (select FROM_USER from settlement_requests where request_id = I_REQUEST_ID);

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Settlement request not found.';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Settlement approved.';
        END IF;

    ELSIF I_OPERATION_STATE = 'REJECT' THEN
        UPDATE SETTLEMENT_REQUESTS
           SET STATUS_CODE = 1003, MODIFIED_ON = CURRENT_TIMESTAMP
         WHERE REQUEST_ID = I_REQUEST_ID;
         
        UPDATE SPLITS SET STATUS_CODE = 1003 where expense_id in (select expense_id from expenses where group_id = I_GROUP_ID AND PAID_BY = I_USER_ID)
        AND USER_ID = (select FROM_USER from settlement_requests where request_id = I_REQUEST_ID);

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Settlement request not found.';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Settlement rejected.';
        END IF;

    ELSE
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Invalid operation state.';
    END IF;

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        O_STATUS_CODE := 991;
        O_STATUS_MESSAGE := 'Duplicate settlement request.';
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error in PRC_MANAGE_SETTLEMENT: ' || SQLERRM;
END PRC_SETTLEMENT_MANAGE;