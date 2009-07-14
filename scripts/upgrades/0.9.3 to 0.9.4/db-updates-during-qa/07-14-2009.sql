-- KULRICE-3212 
ALTER TABLE KREW_DOC_HDR_T MODIFY (APP_DOC_ID VARCHAR2(255))
/

-- KULRICE-3015 - Standardize length of document type name and lbl columns
ALTER TABLE KREW_ACTN_ITM_T MODIFY (DOC_TYP_NM VARCHAR2(64))
/
ALTER TABLE KREW_OUT_BOX_ITM_T MODIFY (DOC_TYP_NM VARCHAR2(64))
/
ALTER TABLE KREW_DOC_TYP_T MODIFY (DOC_TYP_NM VARCHAR2(64))
/
ALTER TABLE KREW_RULE_T MODIFY (DOC_TYP_NM VARCHAR2(64))
/
ALTER TABLE KREW_EDL_ASSCTN_T MODIFY (DOC_TYP_NM VARCHAR2(64))
/
ALTER TABLE KREW_EDL_DMP_T MODIFY (DOC_TYP_NM VARCHAR2(64))
/
ALTER TABLE KREW_DOC_TYP_T MODIFY (LBL VARCHAR2(128))
/
ALTER TABLE KREW_ACTN_ITM_T MODIFY (DOC_TYP_LBL VARCHAR2(128))
/
ALTER TABLE KREW_OUT_BOX_ITM_T MODIFY (DOC_TYP_LBL VARCHAR2(128))
/


