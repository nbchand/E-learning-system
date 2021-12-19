//deletes course
$('.del').on('click',function(event){
    event.preventDefault()
    const id = event.target.id.substring(1);
    fetch("/teacher/course/"+id, {
        method: 'DELETE',
    })
        .then(response => response.text())
        .then(data => {
            if(data=="failed"){
                return
            }
            location.href = "/teacher/courses";
            console.log(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
})