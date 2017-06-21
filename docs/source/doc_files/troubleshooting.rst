Troubleshooting the UI
======================

Grails Error on First Login
---------------------------

This is expected and can be ignored. The error will resolve once a new
user email is successfully submitted to the UI.

Attempting to Initialize Shows "Missing Configuration" Error
------------------------------------------------------------

Sometimes attempting to initialized will show the "Missing
Configuration" error. This is likely because the user has entered their
email address and pressed [ enter ]. This action will throw the above
error every time. The user must ***click*** the "Save & Write
Configuration to Storage" button for the email to be correctly saved.
Until the email is successfully saved the UI will remain un-initialized
and the user will not be able to proceed.

"Sync Options" fields required when creating a new sync
-------------------------------------------------------

These fields are not intended to be required. This is a documented bug
in 3.1 that will be addressed in 3.1.1. The problem shows up when using
Chrome, Internet Explorer, and Firefox browsers. To get around this show
***all*** advanced options fields and enter [space] into each of the
empty fields. Doing this will allow the user to submit a new sync.

Unexplained CAS object failures
-------------------------------

If you cannot find an explanation for CAS object failures, try turning
on `CAS SDK logging <Turning-on-CAS-SDK-logging>`__, which may provide
additional info.
