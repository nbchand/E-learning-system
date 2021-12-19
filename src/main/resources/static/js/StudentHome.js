//deletes course
$('.del').on('click',function(event){
    event.preventDefault()
    const id = event.target.id;
    fetch("/student/course/"+id, {
        method: 'DELETE',
    })
        .then(response => response.text())
        .then(data => {
            if(data=="failed"){
                return
            }
            location.href = "/student/courses";
            console.log(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
})