user registers via email and pass \
which then generates an auth token on front end \
and then sends that token to backend to create that user 

logging in is basically the same as registering \
backend recieves email and auth token and returns \
true and that token if valid data \
only false if invalid data

in order to create/edit posts user has to send that token along with the request 