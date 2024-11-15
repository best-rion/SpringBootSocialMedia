function like(id)
{
	fetch
	(
		"http://localhost:8080/like",
		{
			method: 'PUT',
			headers: {'Content-Type': 'text/plain'},
			body: id
		}
	)
	.then(response=>response.text())
	.then(body=>{
		if (body=="1")
		{
			document.getElementById("like-"+id).style.display = "none"
			document.getElementById("dislike-"+id).style.display = "inline"

			var numOfLikes = document.getElementById("numOfLikes-"+id)
			numOfLikes.innerHTML = ""+(parseInt(numOfLikes.innerHTML) + 1)
		}
	})
}
		
function dislike(id)
{
	fetch
	(
		"http://localhost:8080/dislike",
		{
			method: 'PUT',
			headers: {'Content-Type': 'text/plain'},
			body: id
		}
	)
	.then(response=>response.text())
	.then(body=>{
		if (body=="1")
		{
			document.getElementById("like-"+id).style.display = "inline"
			document.getElementById("dislike-"+id).style.display = "none"

			var numOfLikes = document.getElementById("numOfLikes-"+id)
			numOfLikes.innerHTML = ""+(parseInt(numOfLikes.innerHTML) - 1)
		}
	})
}