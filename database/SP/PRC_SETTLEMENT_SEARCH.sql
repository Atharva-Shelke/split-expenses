create or replace PROCEDURE PRC_SETTLEMENT_SEARCH (
    I_USER_ID         IN SETTLEMENT_REQUESTS.FROM_USER%TYPE,
    I_GROUP_ID        IN SETTLEMENT_REQUESTS.GROUP_ID%TYPE,
    I_TOGGLE_VIEW     IN NUMBER,
    I_OPERATION_STATE IN VARCHAR2,
    I_START           IN NUMBER DEFAULT 0,
    I_LIMIT           IN NUMBER DEFAULT 10,
    O_RESULT          OUT SYS_REFCURSOR,
    O_STATUS_CODE     OUT NUMBER,
    O_STATUS_MESSAGE  OUT VARCHAR2
)
AS
    v_start NUMBER := NVL(I_START, 0);
    v_limit NUMBER := NVL(I_LIMIT, 10);
    v_end   NUMBER := v_start + v_limit;
BEGIN
    IF I_OPERATION_STATE = 'FETCH_ALL' THEN
        OPEN O_RESULT FOR
            SELECT * FROM (
                SELECT ROWNUM AS RN,
                       S.REQUEST_ID, S.GROUP_ID,
                       S.FROM_USER, FU.USERNAME AS FROM_USERNAME,
                       S.TO_USER, TU.USERNAME AS TO_USERNAME,
                       S.AMOUNT, ST.STATUS_CODE, ST.STATUS_DESCRIPTION,
                       TO_CHAR(S.REQUESTED_ON, 'DD-MON-YYYY') AS CREATED_ON,
                       NVL(TO_CHAR(S.MODIFIED_ON,'DD-MON-YYYY'),'NO RESPONSE YET') AS MODIFIED_ON
                  FROM SETTLEMENT_REQUESTS S
                  JOIN USERS FU ON S.FROM_USER = FU.USER_ID
                  JOIN USERS TU ON S.TO_USER = TU.USER_ID
                  JOIN STATUS ST ON S.STATUS_CODE = ST.STATUS_CODE
                 WHERE S.GROUP_ID = I_GROUP_ID AND I_USER_ID =
                 CASE WHEN I_TOGGLE_VIEW = 1 THEN S.FROM_USER ELSE S.TO_USER END
                 ORDER BY S.REQUESTED_ON DESC
            )
            WHERE RN > v_start AND RN <= v_end;

        O_STATUS_CODE := 100;
        IF I_TOGGLE_VIEW = 0 THEN
        O_STATUS_MESSAGE := 'Sent settlemens fetched successfully.';
        ELSE
        O_STATUS_MESSAGE := 'Received settlements fetched successfully.';
        END IF;

    ELSE
        OPEN O_RESULT FOR SELECT * FROM DUAL WHERE 1=2;
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Invalid operation state.';
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        O_STATUS_CODE := 999;
        O_STATUS_MESSAGE := 'Error in PRC_SEARCH_SETTLEMENT: ' || SQLERRM;
END PRC_SETTLEMENT_SEARCH;