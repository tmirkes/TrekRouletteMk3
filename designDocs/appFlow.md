## Application Flow: Trek Roulette
#### Home Page
1. Brief explanation of the purpose of the site in sidebar
2. Random episode generation (q.v.) populating main body above the fold

#### Random Episode Generation
1. User loads home page
2. User receives an episode suggestion based on login state
   1. Users registered and logged in receive recommendations they have not marked "watched" from among the series they have indicated they have access to.
   2. Users not registered or not logged in receive recommendations from the whole of the database; any series, any episode.
3. Users logged in may track episode status (q.v.) (unregistered or logged-out users cannot alter viewing status of recommended episodes).
4. Users may click "Engage Again" to receive another recommendation following the same criteria as the first.

#### Account Registration
1. User clicks "Register" button in the header (common to entire site)
2. User fills out the form (includes series available) and submits
3. Request goes to sign up servlet
4. Servlet creates a user object
5. DAO commits user data to the database via insert query
6. Response to user confirms account creation
7. Timed redirect sends user to home page

#### Document Series Available to User
1. User selects check boxes to identify series and seasons available
2. If user has access to entire series, user may check "Complete series" box
3. User submits form
4. Request goes to profile edit or sign up (depending on context) servlet
5. Servlet generates user object
6. DAO commits user data to the database via update or insert contextual query
7. Response to user confirms data addition or update contextually

#### User Login
1. User clicks on "Sign In" button in the header (common to entire site)
2. User enters user name and password
3. User clicks submit
4. Request goes to authentication servlet
5. User credentials are authenticated
6. Response provides success or failure message based on success or failure of authentication

#### User Logout
1. User clicks user icon in header (common to entire site when logged in)
2. User selects "Log Out" from dropdown menu
3. Request goes to logout servlet
4. User credentials are revoked/cleared
5. Response provides confirmation message
6. Timed redirect sends user to home page

#### Track Episode Status
1. User logs in (q.v.)
2. User receives a random recommendation (q.v.)
3. User clicks "Start Watching"
4. Request goes to status update servlet
5. DAO generates update and processes status change in database
6. Response changes button to "Done" state
   1. Clicking "Done" resolves procedure again
   2. Response condition changes state to "Watched" and grays out control

#### Modify Database with New Series/Episodes
1. User logs in (q.v.) and has admin privileges
2. User clicks user icon in header (common to entire site when logged in)
3. User selects "Update Database" from dropdown menu (visible only to admin users)
4. Request goes out to database update servlet
5. Servlet creates a database update object
6. DAO queries the database and checks for new data via STAPI
7. If no new data (current database row count matches API database), response provides "Database is Current" message
8. If new data available (current database row count is less than API database) DAO queries database for new data
9. New data is committed to local tables by DAO object
10. Response indicates count of new episodes added and provides "Database Updated" message

#### About Trek Roulette
1. Static HTML document
2. Web contact form
3. Brief explanation of the functionality of the site