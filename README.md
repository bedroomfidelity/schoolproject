# schoolproject
School project : a messenger/tasks management system web app for stores using Java EE, Hibernate and Jersey

Rest API URIs :

- User: 
	GET: 	/api/user/all : get all users
		/api/user/{username} get by username
	POST:	/api/user/signup : sign up new user (username,password,title,email,firstname,lastname,address,phonenumber,image)
		/api/user/login : log in (username,password)
	DELETE: /api/user/delete/{username} : delete by username
	PUT: 	/api/user/edit : edit user information (username,password,title,email,firstname,lastname,address,phonenumber,image)

- Task:
	GET: 	/api/task/all : get all tasks
		/api/task/{username} : get all tasks of an user
		/api/task/undone/{username} : get all tasks undone by an user
	POST: 	/api/task/new : add new task (taskname,description,startdate,deadline,worker)
	PUT: 	/api/task/edit: edit a task (id,taskname,description,startdate,deadline,worker,done)
	DELETE: /api/task/delete/{id} : delete a task with given id

- Shift:
	GET: 	/api/shift/all : get all shifts
		/api/shift/getbyname/{name} : get all shifts by name
	POST:	/api/shift/add : add new shift (employee,starttime,endtime)
	PUT:	/api/shift/edit : add new shift (id,employee,starttime,endtime)
	DELETE: /api/shift/delete/{id} : delete a shift by id

- Comment: 
	GET: 	/api/comment/{taskid} : get all comments in a task
	POST: 	/api/comment/add : add a new comment (commenter,taskid,content)
	PUT:	/api/comment/add : add a new comment (commentid,user,content,taskid)
	DELETE: /api/comment/delete/{id} : delete a comment 

- Message: 
	GET:	/api/message/bysender/{username} : get all messages an user sent
		/api/message/byreceiver/{username} : get all messages an user received
	POST:	/api/message/new : add new message (sender,receiver,content,date,file)
		*notice: to get a file in a message , use this URI for GET method : /api/message/file/{id}
	DELETE:	/api/delete/{id} : delete a message
	
