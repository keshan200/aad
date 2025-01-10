
const fetchItemID=()=>{

    $.ajax({
        url: "http://localhost:8080/Company/od",
        method: "GET",

        success: (res) => {
            $('#ItemName').empty();
            console.log("API Response:", res);

            res.itemArray.forEach((itm) =>{
                let itemID = `<option>${itm.description}</option>`;
                $('#ItemName').append(itemID);
            });
        },

        error: () => {
            console.error("Error occurred:", error);
            console.error("Response text:", xhr.responseText);
            alert("Failed to load Item IDs");
        }
    });
}


fetchItemID()
