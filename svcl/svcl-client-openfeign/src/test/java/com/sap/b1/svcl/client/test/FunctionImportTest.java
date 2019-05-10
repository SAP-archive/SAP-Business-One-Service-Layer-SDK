package com.sap.b1.svcl.client.test;

import org.junit.Test;

import com.sap.b1.svcl.client.complextype.BankStatementParams;
import com.sap.b1.svcl.client.functionimport.BankStatementsService;
import com.sap.b1.svcl.client.functionimport.BankStatementsService.BankStatementsService_GetBankStatementListParam;
import com.sap.b1.svcl.client.utils.ODataList;

public class FunctionImportTest  extends TestBase{
	
	@Test
    public void post() 
    {
    	httpClientMock.onPost("/b1s/v1/BankStatementsService_GetBankStatementList").doReturn(200,"{}");
    	BankStatementsService service = httpClient.target(BankStatementsService.class);
    	BankStatementsService_GetBankStatementListParam param = new BankStatementsService_GetBankStatementListParam();
		@SuppressWarnings("unused")
		ODataList<BankStatementParams> ret = service.getBankStatementList(param);
    	httpClientMock.verify().post("/b1s/v1/BankStatementsService_GetBankStatementList").called();
    }
	
	@Test
    public void get() 
    {

    }

}
