create or replace PROCEDURE PRC_GROUP_MANAGE(
    I_USER_ID IN USERS.USER_ID%TYPE,
    I_GROUP_ID   IN GROUPS.GROUP_ID%TYPE,
    I_GROUP_NAME   IN GROUPS.GROUP_NAME%TYPE,
    I_OPERATION_STATE IN VARCHAR2,
    O_STATUS_CODE OUT NUMBER,
    O_STATUS_MESSAGE OUT VARCHAR2
)
AS
v_username VARCHAR2(100);
v_count NUMBER;
BEGIN
    SELECT USERNAME into v_username FROM USERS WHERE USER_ID = I_USER_ID;

    IF I_OPERATION_STATE <> 'CREATE' THEN
        SELECT count(1) into v_count FROM GROUPS WHERE GROUP_ID = I_GROUP_ID AND CREATED_BY_ID = I_USER_ID;
    END IF;

    IF I_OPERATION_STATE = 'CREATE' THEN
        INSERT INTO GROUPS (GROUP_NAME, CREATED_BY, CREATED_BY_ID)
        VALUES (I_GROUP_NAME, v_username, I_USER_ID );

        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'Group '||I_GROUP_NAME||' created successfully';

    ELSIF I_OPERATION_STATE = 'UPDATE' THEN 
        IF v_count=0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Only Group Owner can update the Group.';
            return;
        END IF;

        UPDATE GROUPS
           SET GROUP_NAME = I_GROUP_NAME, MODIFIED_ON = CURRENT_TIMESTAMP
         WHERE GROUP_ID = I_GROUP_ID AND CREATED_BY_ID = I_USER_ID;

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Group not found';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Group updated successfully';
        END IF;

    ELSIF I_OPERATION_STATE = 'DELETE' THEN
        IF v_count = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Only Group Owner can delete the Group.';
            return;
        END IF;
        
        SELECT count(*) into v_count FROM EXPENSES WHERE GROUP_ID = I_GROUP_ID AND PAID_BY IN (SELECT MEMBER_ID FROM GROUP_MEMBERS WHERE GROUP_ID = I_GROUP_ID AND MEMBER_ID <> I_USER_ID);
        IF v_count > 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Cannot delete as member has spent in this group.';
            return;
        END IF;
        
        SELECT count(*) into v_count FROM SETTLEMENT_REQUESTS WHERE GROUP_ID = I_GROUP_ID;
        IF v_count > 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'Cannot delete as settlement is made in this group.';
            return;
        END IF;

        DELETE FROM EXPENSES WHERE GROUP_ID = I_GROUP_ID;
        DELETE FROM GROUP_MEMBERS WHERE GROUP_ID = I_GROUP_ID;
        DELETE FROM GROUPS WHERE GROUP_ID = I_GROUP_ID;

            IF SQL%ROWCOUNT = 0 THEN
                ROLLBACK;
                O_STATUS_CODE := 991;
                O_STATUS_MESSAGE := 'Group not found';
            ELSE
            
                O_STATUS_CODE := 100;
                O_STATUS_MESSAGE := 'Group deleted successfully';
            END IF;
    END IF;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        O_STATUS_CODE := 991;
        O_STATUS_MESSAGE := 'Group already exists';
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error occured in PRC_GROUP_MANAGE: ' || SQLERRM;
END PRC_GROUP_MANAGE;