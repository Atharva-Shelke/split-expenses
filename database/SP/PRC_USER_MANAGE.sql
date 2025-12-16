create or replace PROCEDURE PRC_USER_MANAGE (
    I_USER_ID IN USERS.USER_ID%TYPE,
    I_USERNAME   IN USERS.USERNAME%TYPE,
    I_EMAIL      IN USERS.EMAIL%TYPE,
    I_MOBILE_NO  IN USERS.MOBILE_NO%TYPE,
    I_PASSWORD   IN USERS.PASSWORD%TYPE,
    I_OPERATION_STATE IN VARCHAR2,
    O_STATUS_CODE OUT NUMBER,
    O_STATUS_MESSAGE OUT VARCHAR2
)
AS
v_count NUMBER;
BEGIN
    IF I_OPERATION_STATE = 'CREATE' THEN
        INSERT INTO USERS (USERNAME, EMAIL, MOBILE_NO, PASSWORD)
        VALUES (I_USERNAME, I_EMAIL, I_MOBILE_NO, I_PASSWORD);

        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'User '||I_USERNAME||' registered successfully';

    ELSIF I_OPERATION_STATE = 'UPDATE' THEN

        UPDATE USERS
           SET USERNAME = I_USERNAME,
               EMAIL = I_EMAIL,
               MOBILE_NO = I_MOBILE_NO,
               MODIFIED_ON = CURRENT_TIMESTAMP
         WHERE USER_ID = I_USER_ID;

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'User not found';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'User updated successfully';
        END IF;

        ELSIF I_OPERATION_STATE = 'UPDATE_PASSWORD' THEN

        UPDATE USERS
           SET PASSWORD = I_PASSWORD,
               MODIFIED_ON = CURRENT_TIMESTAMP
         WHERE USERNAME = I_USERNAME;

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'User '||I_USERNAME||' not found';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Password for '||I_USERNAME||' updated successfully';
        END IF;

    ELSIF I_OPERATION_STATE = 'DELETE' THEN

        DELETE FROM USERS WHERE USER_ID = I_USER_ID;

        IF SQL%ROWCOUNT = 0 THEN
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'User not found';
        ELSE
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'User deleted successfully';
        END IF;
    END IF;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        O_STATUS_CODE := 991;
        O_STATUS_MESSAGE := 'User already exists';
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error occured in PRC_USER_MANAGE: ' || SQLERRM;
END PRC_USER_MANAGE;