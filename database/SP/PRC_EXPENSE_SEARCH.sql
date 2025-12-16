create or replace PROCEDURE PRC_EXPENSE_SEARCH(
    I_USER_ID         IN USERS.USER_ID%TYPE,    
    I_GROUP_ID        IN EXPENSES.GROUP_ID%TYPE,  
    I_PAID_BY         IN EXPENSES.PAID_BY%TYPE,
    I_TOGGLE_VIEW     IN NUMBER,
    I_OPERATION_STATE IN VARCHAR2,
    I_START           IN NUMBER,
    I_LIMIT           IN NUMBER,
    O_RESULT          OUT SYS_REFCURSOR,
    O_STATUS_CODE     OUT NUMBER,
    O_STATUS_MESSAGE  OUT VARCHAR2
)
AS
v_start NUMBER := NVL(I_START, 0);
v_limit NUMBER := NVL(I_LIMIT, 10);
v_end NUMBER := v_start + v_limit;
v_cnt NUMBER;
v_request_to NUMBER;
v_request_from NUMBER;
v_ttl_splt_amt NUMBER;
v_can_request NUMBER := 0;
v_req_exist NUMBER := 0;
v_existing_req_status NUMBER:=0;
BEGIN
    IF I_OPERATION_STATE = 'FETCH_ALL' THEN
        SELECT count(*) INTO v_cnt FROM EXPENSES EX WHERE EX.GROUP_ID = I_GROUP_ID AND EX.PAID_BY = I_PAID_BY
        AND ( I_TOGGLE_VIEW = 0 OR EXISTS (
        SELECT 1 FROM SPLITS S2 WHERE S2.EXPENSE_ID = EX.EXPENSE_ID AND S2.USER_ID = I_USER_ID)
        );
        
        IF v_cnt > 0 THEN
            OPEN O_RESULT FOR
                SELECT T.* FROM (SELECT ROWNUM as RN, EX.EXPENSE_ID, EX.GROUP_ID, EX.PAID_BY, U.USERNAME as CREATED_BY, EX.DESCRIPTION, EX.AMOUNT, TO_CHAR(EX.CREATED_ON,'DD-MON-YYYY') as CREATED_ON
                , EX.MEMBER_IDS, (SELECT LISTAGG(U2.USERNAME, ',') WITHIN GROUP (ORDER BY U2.USERNAME) FROM SPLITS S
                 JOIN USERS U2 ON S.USER_ID = U2.USER_ID WHERE S.EXPENSE_ID = EX.EXPENSE_ID) AS MEMBER_NAMES, NVL(S1.AMOUNT_OWED,0) AS SPLIT_AMOUNT
               FROM EXPENSES EX
                  JOIN USERS U ON EX.PAID_BY = U.USER_ID
                  LEFT JOIN SPLITS S1 ON S1.EXPENSE_ID = EX.EXPENSE_ID AND S1.USER_ID = I_USER_ID
                 WHERE EX.GROUP_ID = I_GROUP_ID AND EX.PAID_BY = I_PAID_BY
                       AND (
                I_TOGGLE_VIEW = 0
                OR EXISTS (
                    SELECT 1 FROM SPLITS S2
                    WHERE S2.EXPENSE_ID = EX.EXPENSE_ID
                      AND S2.USER_ID = I_USER_ID
                )
              )
                 ORDER BY EX.CREATED_ON DESC)T WHERE T.RN > v_start AND T.RN <= v_end;

            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'All Expenses fetched successfully';
        ELSE
            OPEN O_RESULT FOR SELECT * FROM DUAL WHERE 1=2;
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'No Record found';
        END IF;

    ELSIF I_OPERATION_STATE = 'FETCH_BY_PARAM' THEN
        OPEN O_RESULT FOR
            SELECT T.* FROM (SELECT ROWNUM as RN, EX.EXPENSE_ID, EX.GROUP_ID, EX.PAID_BY, U.USERNAME, EX.DESCRIPTION, EX.AMOUNT, TO_CHAR(EX.CREATED_ON,'DD-MON-YYYY') as CREATED_ON,
            SUM(EX.AMOUNT) OVER (PARTITION BY EX.GROUP_ID, EX.PAID_BY) as TOTAL_EXPENSES
              FROM EXPENSES EX
              JOIN USERS U ON EX.PAID_BY = U.USER_ID
             WHERE EX.GROUP_ID = I_GROUP_ID AND EX.PAID_BY = I_PAID_BY
             ORDER BY EX.CREATED_ON DESC)T WHERE T.RN > v_start AND T.RN <= v_end;
        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'Expenses fetched successfully';

    ELSIF I_OPERATION_STATE = 'FETCH_TOTAL' THEN
        OPEN O_RESULT FOR
            SELECT EX.GROUP_ID, EX.PAID_BY, U.USERNAME, SUM(EX.AMOUNT) as TOTAL_EXPENSE
              FROM EXPENSES EX
              JOIN USERS U ON EX.PAID_BY = U.USER_ID
             WHERE EX.GROUP_ID = I_GROUP_ID AND EX.PAID_BY = I_PAID_BY
             GROUP BY EX.GROUP_ID, EX.PAID_BY, U.USERNAME;
        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'Total Expense fetched successfully';

    ELSIF I_OPERATION_STATE = 'SPLIT_REQUEST' THEN
        SELECT NVL(SUM(S.AMOUNT_OWED),0) into v_request_to FROM EXPENSES EX
        JOIN SPLITS S ON S.EXPENSE_ID = EX.EXPENSE_ID AND S.USER_ID = I_USER_ID
        WHERE EX.GROUP_ID = I_GROUP_ID AND EX.PAID_BY = I_PAID_BY
        AND S.STATUS_CODE in (1004,1003);
        
        SELECT NVL(SUM(S.AMOUNT_OWED),0) into v_request_from FROM EXPENSES EX
        JOIN SPLITS S ON S.EXPENSE_ID = EX.EXPENSE_ID AND S.USER_ID = I_PAID_BY
        WHERE EX.GROUP_ID = I_GROUP_ID AND EX.PAID_BY = I_USER_ID
        AND S.STATUS_CODE in (1004,1003);
        
        v_ttl_splt_amt:=v_request_to-v_request_from;
        
        SELECT count(*) into v_req_exist FROM SETTLEMENT_REQUESTS WHERE GROUP_ID = I_GROUP_ID AND FROM_USER = I_USER_ID AND TO_USER = I_PAID_BY;
            
        IF v_ttl_splt_amt > 0 THEN
            IF v_req_exist = 0 THEN
                v_can_request := 1;
            ELSE
            SELECT STATUS_CODE INTO v_existing_req_status FROM SETTLEMENT_REQUESTS WHERE GROUP_ID = I_GROUP_ID AND FROM_USER = I_USER_ID AND TO_USER = I_PAID_BY;
                
                if v_existing_req_status in (1000,1001) then
                    v_can_request := 0;
                elsif v_existing_req_status=1003 then
                    v_can_request := 1;
                end if;
            END IF;
        END IF;
        
        OPEN O_RESULT FOR
            SELECT 
                v_ttl_splt_amt AS TOTAL_SPLIT_AMOUNT,
                v_can_request AS CAN_REQUEST,
                v_existing_req_status as EXISTING_REQUEST_STATUS
            FROM dual;

        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'Split details fetched successfully';
    ELSE
        OPEN O_RESULT FOR SELECT * FROM DUAL WHERE 1=2;
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Invalid operation state';
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := SQLERRM;
END PRC_EXPENSE_SEARCH;