function deletePost(id)
{
	fetch(
		"http://10.18.122.174:8080/delete/post/"+id,
		{
			method: 'DELETE'
		}
	)
}