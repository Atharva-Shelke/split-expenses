create or replace PROCEDURE PRC_EXPENSE_MANAGE (
    I_USER_ID          IN EXPENSES.PAID_BY%TYPE,
    I_GROUP_ID         IN EXPENSES.GROUP_ID%TYPE,
    I_DESCRIPTION      IN EXPENSES.DESCRIPTION%TYPE,
    I_AMOUNT           IN EXPENSES.AMOUNT%TYPE,
    I_MEMBER_IDS       IN EXPENSES.MEMBER_IDS%TYPE,  -- comma-separated user IDs
    I_EXPENSE_ID       IN EXPENSES.EXPENSE_ID%TYPE DEFAULT 0,
    I_OPERATION_STATE  IN VARCHAR2,
    O_STATUS_CODE      OUT NUMBER,
    O_STATUS_MESSAGE   OUT VARCHAR2
)
AS
v_count NUMBER;
BEGIN
    IF I_OPERATION_STATE = 'CREATE' THEN
        INSERT INTO EXPENSES (GROUP_ID, PAID_BY, DESCRIPTION, AMOUNT, MEMBER_IDS)
        VALUES (I_GROUP_ID, I_USER_ID, I_DESCRIPTION, I_AMOUNT, I_MEMBER_IDS);

        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'Expense created successfully.';

    ELSIF I_OPERATION_STATE = 'DELETE' THEN
--        DELETE FROM SPLITS WHERE EXPENSE_ID = I_EXPENSE_ID;
        SELECT COUNT(*)INTO v_count FROM SETTLEMENT_REQUESTS WHERE STATUS_CODE in (1000,1001) AND GROUP_ID = I_GROUP_ID
        AND TO_USER = (select user_id from splits where expense_id = I_EXPENSE_ID and user_id = I_USER_ID) 
        AND FROM_USER IN (select user_id from splits where expense_id = I_EXPENSE_ID);
        
        IF v_count > 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Expense cannot be deleted as requests are present for this.';
            return;
        END IF;
        
        DELETE FROM EXPENSES WHERE EXPENSE_ID = I_EXPENSE_ID;

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Expense not found for delete.';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Expense deleted successfully.';
        END IF;

    ELSE
        O_STATUS_CODE := 991;
        O_STATUS_MESSAGE := 'Invalid operation state.';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error in PRC_MANAGE_EXPENSE: ' || SQLERRM;
END PRC_EXPENSE_MANAGE;