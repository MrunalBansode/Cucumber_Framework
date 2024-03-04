Feature: Login with Valid Credentials

@sanity
   Scenario: Successful Login with valid credentials
		Given User Launch browser
		And opens URL "http://localhost/opencart/upload/"
		When User navigate to MyAccount menu
		And click on login
		And User enter Email as "mrunalb63@gmail.com" and Password as "Pass123"
		And Click on Login button
		Then User navigates to MyAccount Page