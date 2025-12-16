create or replace PROCEDURE PRC_GROUP_MEMBER_SEARCH(
    I_USER_ID         IN USERS.USER_ID%TYPE,
    I_GROUP_ID        IN GROUP_MEMBERS.GROUP_ID%TYPE,
    I_MEMBER_ID       IN GROUP_MEMBERS.MEMBER_ID%TYPE,
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
v_cnt Number;
BEGIN
    IF I_OPERATION_STATE = 'FETCH_ALL' THEN
        select count(*) into v_cnt from GROUP_MEMBERS WHERE GROUP_ID = I_GROUP_ID;
        if v_cnt = 0 then
            OPEN O_RESULT FOR SELECT * FROM DUAL WHERE 1=2;
            O_STATUS_CODE := 991;
            O_STATUS_MESSAGE := 'No members in group yet';
        
        else
            OPEN O_RESULT FOR
                SELECT T.* FROM (SELECT ROWNUM as RN, GM.GROUP_ID, GM.MEMBER_ID, U.USERNAME AS MEMBER_NAME, TO_CHAR(GM.ADDED_ON,'DD-MON-YYYY') as ADDED_ON, MEMBER_TYPE
                  FROM GROUP_MEMBERS GM
                  JOIN USERS U ON GM.MEMBER_ID = U.USER_ID
                 WHERE GM.GROUP_ID = I_GROUP_ID 
                 ORDER BY GM.ADDED_ON)T WHERE T.RN > v_start AND T.RN <= v_end;
            O_STATUS_CODE := 100;
            O_STATUS_MESSAGE := 'Members fetched successfully';        
        end if;

    ELSIF I_OPERATION_STATE = 'FETCH_BY_PARAM' THEN
        
            OPEN O_RESULT FOR
            SELECT GM.GROUP_ID, GM.MEMBER_ID, U.USERNAME, TO_CHAR(GM.ADDED_ON,'DD-MON-YYYY') as ADDED_ON
              FROM GROUP_MEMBERS GM
              JOIN USERS U ON GM.MEMBER_ID = U.USER_ID
             WHERE GM.GROUP_ID = I_GROUP_ID AND MEMBER_ID = I_MEMBER_ID;
             
        O_STATUS_CODE := 100;
        O_STATUS_MESSAGE := 'Member fetched successfully';

    ELSE
        OPEN O_RESULT FOR SELECT * FROM DUAL WHERE 1=2;
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Invalid operation state';
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error occured in PRC_GROUP_MEMBER_SEARCH: ' || SQLERRM;
END PRC_GROUP_MEMBER_SEARCH;