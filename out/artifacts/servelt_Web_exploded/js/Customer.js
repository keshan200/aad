
const fetchData = ()=>{

    $.ajax({

        url:"http://localhost:8080/Company/cus",
        method:"GET",

        success: (response)=>{

            $('#tbody').empty()

            response.forEach((cus)=>{

                let Data =
                    `<tr>
                            <td>${cus.id}</td>
                            <td>${cus.name}</td>
                            <td>${cus.address}</td>
                            
                            
                    <td>
                     <button class="btn btn-success" id="btnUpdate"class="btn btn-primary mb-3 text-right" data-bs-toggle="modal"
                      data-bs-target="#updateModal" onclick="edit('${cus.id}','${cus.name}','${cus.address}')" )">Edit</button>
                      
                     <button class="btn btn-danger" id="btnDelete" onclick="Delete('${cus.id}')" )">Delete</button>
                   </td>
                            
                      </tr>               
                    `
                $('#tbody').append(Data)

            })
        },


        error:()=>{
                     console.log("error")
        }

    })

}

fetchData();



$("#btnSave").click(()=>{

    let id = $("#id").val();
    let name = $("#name").val();
    let addrs = $("#address").val();


    $.ajax({

        url: `http://localhost:8080/Company/cus?id=${id}&name=${name}&address=${addrs}`,
        method: "POST",

        success:()=>{
            fetchData();
            Swal.fire({
                title: "Added!",
                icon: "success",
                draggable: true
            });
        },

        error:()=>{
            console.log("error")
        }


    })

})

const Delete = (id)=>{
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `http://localhost:8080/Company/cus?id=${id}`,
                method: 'DELETE',

                success: () => {
                    fetchData()
                },
                error: () => {
                    console.log("cant delete")
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Something went wrong!",
                        footer: '<a href="#">Why do I have this issue?</a>'
                    });
                }
            })
            Swal.fire({
                title: "Deleted!",
                text: "Your file has been deleted.",
                icon: "success"
            });
        }
    });
}

$("#btnUpdate").click(()=>{

    let id = $("#idU").val();
    let name = $("#nameU").val();
    let addrs = $("#addressU").val();


    $.ajax({
        url: `http://localhost:8080/Company/cus?id=${id}&name=${name}&address=${addrs}`,
        method:"PUT",

        success:()=>{
            fetchData();
        },

        error:()=>{
            console.log("error")
        }

    })

})

const edit = (id,name,address)=>{
  $("#idU").val(id);
  $("#nameU").val(name);
  $("#addressU").val(address);
}
