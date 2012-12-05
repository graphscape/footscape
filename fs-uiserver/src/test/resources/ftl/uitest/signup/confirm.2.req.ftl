<#--test confirm failed:
TODO:get code by receiving mail Helper.forName('com.fs.hdls.uiserver.impl.test.MailReceiverI')

-->
<#function confirmCode>
	<#local container = Helper.container>
	<#local hcls = Helper.forName('com.fs.uiserver.TestHelperI')>
	<#local testHelper = container.find(hcls,true)>
	<#local confirmCode = testHelper.getConfirmCode('user1@domain1.com',true)>
	<#return confirmCode >
</#function>

["_O",{"email":["_S","user1@domain1.com"],"confirmCode":["_S","${confirmCode()}"]}]