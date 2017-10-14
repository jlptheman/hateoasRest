# hateoas Rest

## Sample Bank Loan Application system
Simple demonstration to show how to manage client state from the server in a REST environment.

User Guide:
Using Postman (or similar), send the following commands:
1. Request a new loan application Id:
   -  http://localhost:8080/v1/loan
1. Send Application Form
   -  if you look in the response from previous command, you will see 2 links:
      1. A link to view this Loan Application again, at any time
      1. A link to send your Application Form to.
   - Click this link
   - change Verb to POST
   - Go to request body and past in the following text 
      - { "amount" : "600" }
   - Send request
1. Send forms if required
   - Depending on loan Amount specified above, you may have to send additional forms
   - If less than 500, no proof of identification required
   - If greater than 500 and less than 5000, you need to send details of your Drivers License
      - If so, the link for sending your drivers license is provided in the previous response
   - ... work in progress...