create or replace PROCEDURE PRC_USER_SEARCH (
    I_USER_ID         IN USERS.USER_ID%TYPE,
    I_USERNAME        IN USERS.USERNAME%TYPE,
    I_EMAIL           IN USERS.EMAIL%TYPE,
    I_OPERATION_STATE IN VARCHAR2,
    I_START           IN NUMBER,
    I_LIMIT           IN NUMBER,
    O_RESULT          OUT SYS_REFCURSOR,
    O_STATUS_CODE     OUT NUMBER,
    O_STATUS_MESSAGE  OUT VARCHAR2
)
AS
v_param VARCHAR2(256);
v_count NUMBER;
v_start NUMBER := NVL(I_START, 0);
v_limit NUMBER := NVL(I_LIMIT, 10);
v_end NUMBER := v_start + v_limit;
BEGIN

    IF I_OPERATION_STATE = 'FETCH_ALL' THEN
        OPEN O_RESULT FOR SELECT T.* FROM (SELECT ROWNUM AS RN, USER_ID, USERNAME
            FROM USERS)T WHERE T.RN > v_start AND T.RN <= v_end;
        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'All users fetched successfully.';

    ELSIF I_OPERATION_STATE = 'FETCH_BY_PARAM' THEN

        SELECT COUNT(1) INTO v_count FROM USERS WHERE USERNAME = I_USERNAME;
        IF v_count = 1 THEN
            OPEN O_RESULT FOR
                SELECT USER_ID, USERNAME
                FROM USERS
                WHERE USERNAME = I_USERNAME;
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'User fetched successfully.';
        ELSE
            OPEN O_RESULT FOR SELECT * FROM DUAL WHERE 1=2;
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'User not found.';
        END IF;

    ELSE
        OPEN O_RESULT FOR SELECT * FROM DUAL WHERE 1=2;
        O_STATUS_CODE := 991;
        O_STATUS_MESSAGE := 'Invalid operation state.';
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error occured in PRC_USER_SEARCH: ' || SQLERRM;
END PRC_USER_SEARCH;