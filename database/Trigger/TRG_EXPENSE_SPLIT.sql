create or replace TRIGGER TRG_EXPENSE_SPLIT
AFTER INSERT OR UPDATE ON EXPENSES
FOR EACH ROW
DECLARE
    v_participants SYS.ODCINUMBERLIST := SYS.ODCINUMBERLIST();
    v_amount_owed NUMBER;
BEGIN
    
    IF :NEW.MEMBER_IDS IS NOT NULL THEN
        SELECT TO_NUMBER(REGEXP_SUBSTR(:NEW.MEMBER_IDS, '[^][,]+', 1, LEVEL))
        BULK COLLECT INTO v_participants
        FROM dual
        CONNECT BY REGEXP_SUBSTR(:NEW.MEMBER_IDS, '[^][,]+', 1, LEVEL) IS NOT NULL;

        IF v_participants.COUNT > 0 THEN
            v_amount_owed := ROUND(:NEW.AMOUNT / v_participants.COUNT, 2);
            FOR i IN 1 .. v_participants.COUNT LOOP
                INSERT INTO SPLITS (EXPENSE_ID, USER_ID, AMOUNT_OWED)
                VALUES (:NEW.EXPENSE_ID, v_participants(i), v_amount_owed);
            END LOOP;
            UPDATE SPLITS SET STATUS_CODE = 1001 WHERE USER_ID = :NEW.PAID_BY;
        END IF;
    END IF;
END TRG_EXPENSE_SPLIT;