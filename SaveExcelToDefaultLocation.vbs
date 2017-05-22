'save excel file to default loacation.

Dim returnValue
Dim checkOpenWindow
Dim objShell
Dim counter
counter = 0

Set objShell = WScript.CreateObject( "WScript.Shell" )
'Set checkOpenWindow = CreateObject("WScript.Shell")

Do
	'ret = checkOpenWindow.AppActivate("Opening Contractor Management Outlook report.xlsx")
	 ret = objShell.AppActivate("Opening Contractor Management Outlook report.xlsx")
		'ret = objShell.AppActivate("compare result.txt - Notepad")
	If ret = True Then 
		objShell.SendKeys "{DOWN}"
			WScript.Sleep 2000
		objShell.SendKeys "{ENTER}"
		Exit Do	
	Elseif counter = 600 Then
		Exit Do
	End if
	Set ret = nothing
	counter = counter + 1
	WScript.Sleep 1000
Loop

WScript.Quit(returnValue) 'Return value for Java