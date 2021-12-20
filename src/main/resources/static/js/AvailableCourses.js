//take checkbox values to the controller
$('#courseForm').submit(function(event) {
    event.preventDefault();

    var courses = [];
    $("input:checkbox:checked").each(function () {
        courses.push($(this).val());
    });

    if (courses.length === 0) {
        $('#jsError').html("<div class='alert-danger mt-3 p-2 text-center h3'>No courses selected</div>");
        window.scrollTo(0, 0);
        return;
    }
    ;

    const data = JSON.stringify(courses);
    console.log(data);
    fetch("/student/enroll", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: data,
    })
        .then(response => response.text())
        .then(data => {
            if(data=="unavailable"){
                location.href = "/";
            }
            else if(data=="teacher"){
                location.href = "/teacher/course";
            }
            else if(data=="success"){
                location.href = "/student/courses";
            }
            else{
                $('#jsError').html("<div class='alert-danger mt-3 p-2 text-center h3'>"+data+"</div>");
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });

})