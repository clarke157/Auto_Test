''Detects if the WindowMessage is open

Dim returnValue
Dim checkOpenWindow
Dim objShell
Dim counter
counter = 0

Set objShell = WScript.CreateObject( "WScript.Shell" )
Set checkOpenWindow = CreateObject("WScript.Shell")

Do
	ret = checkOpenWindow.AppActivate("Opening Contractor Management Outlook report.xlsx")
	If ret = True Then 
		'objShell.SendKeys "%{F4}" 'send ALT F4 command
		returnValue = 1
		Exit Do	
	Elseif counter = 600 Then
		returnValue = 2
		Exit Do
	End if
	Set ret = nothing
	counter = counter + 1
	WScript.Sleep 1000
Loop

WScript.Quit(returnValue) 'Return value for Java