create or replace PROCEDURE PRC_GROUP_MEMBER_MANAGE(
    I_USER_ID         IN USERS.USER_ID%TYPE,
    I_GROUP_ID        IN GROUP_MEMBERS.GROUP_ID%TYPE,
    I_MEMBER_ID       IN GROUP_MEMBERS.MEMBER_ID%TYPE,
    I_OPERATION_STATE IN VARCHAR2,
    O_STATUS_CODE     OUT NUMBER,
    O_STATUS_MESSAGE  OUT VARCHAR2
)
AS
v_count NUMBER;
v_is_owner NUMBER;
BEGIN
    
    IF I_OPERATION_STATE = 'CREATE' THEN
        SELECT COUNT(*) INTO v_count FROM GROUP_MEMBERS 
         WHERE GROUP_ID = I_GROUP_ID AND MEMBER_ID = I_MEMBER_ID;

        IF v_count > 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'User already a member of group';
        ELSE
            INSERT INTO GROUP_MEMBERS (GROUP_ID, MEMBER_ID)
            VALUES (I_GROUP_ID, I_MEMBER_ID);
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Member added successfully';
        END IF;

    ELSIF I_OPERATION_STATE = 'DELETE' THEN
        IF I_USER_ID <> I_MEMBER_ID THEN
            SELECT count(1) into v_is_owner FROM GROUPS WHERE GROUP_ID = I_GROUP_ID AND CREATED_BY_ID = I_USER_ID;
            IF v_is_owner = 0 THEN
                O_STATUS_CODE := 991;
                O_STATUS_MESSAGE := 'Only Group Owner can add or delete the Group Member.';
                return;
            END IF;
        END IF;
        
        SELECT COUNT(*)INTO v_count FROM SPLITS WHERE EXPENSE_ID in (SELECT EXPENSE_ID FROM EXPENSES WHERE GROUP_ID = I_GROUP_ID AND PAID_BY = I_MEMBER_ID);
        IF v_count > 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Member cannot be deleted as SplitExpense is pending.';
            return;
        END IF;

        DELETE FROM GROUP_MEMBERS WHERE GROUP_ID = I_GROUP_ID AND MEMBER_ID = I_MEMBER_ID;

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Member not found in group';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Member removed successfully';
        END IF;

    ELSE
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Invalid operation state';
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error occured in PRC_GROUP_MEMBER_MANAGE: ' || SQLERRM;
END PRC_GROUP_MEMBER_MANAGE;