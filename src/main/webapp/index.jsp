<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>TrackMyCab REST API doc</title>

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">

		<div class="container">
			<h1>API documentation</h1>
			<h4>The current available REST API is listed below</h4>
			<div class="bs-callout bs-callout-warning">
				<p>Domain : ec2-54-81-11-139.compute-1.amazonaws.com</p>
				<p>Application name : trackmycab</p>
				<p>Rest URL :
					ec2-54-81-11-139.compute-1.amazonaws.com/trackmycab/rest/REST/</p>
			</div>
		</div>

		<div style="border: 1px solid; border-radius: 5px; border-color: gray">
			<h4>Get Status of App</h4>
			<div class="alert alert-success">/statusCheck</div>
		</div>

		<div style="border: 1px solid; border-radius: 5px; border-color: gray">
			<h3>Route Info</h3>
			<h4>Get list of all the routes</h4>
			<div class="alert alert-success">/routeList</div>

			<h4>Get info about a particular route</h4>
			<div class="alert alert-success">/route/{routeNo}</div>
		</div>

		<div style="border: 1px solid; border-radius: 5px; border-color: gray">
			<h3>Cab info</h3>
			<h4>Get list of all the cabs</h4>
			<div class="alert alert-success">/cabList</div>

			<h4>Get status of cab</h4>
			<div class="alert alert-success">/cab/{cabno}</div>

			<h4>Set GPS location of cab</h4>
			<div class="alert alert-info">/cab/{cabNo}/{lattitude}/{longitude}</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
