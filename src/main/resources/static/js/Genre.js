getGenres(loggedIn, user, order);

function getGenres(loggedIn, user, order) {
  fetch("http://localhost:8082/genres/read" + order)
    .then(function (response) {
      if (response.status !== 200) {
        console.log(
          "Looks like there was a problem. Status Code: " + response.status
        );
        return;
      }
      // Examine the text in the response
      response.json().then(function (GenreData) {
        let table = document.querySelector("#GenreTable");
        let data = Object.keys(GenreData[0]);

        generateTableHeadGe(table, data, loggedIn);
        generateTableGe(table, GenreData, loggedIn, user);
        // if (loggedIn) {
        //   generateAddGenreBtn(table);
        // }
      });
    })
    .catch(function (err) {
      console.log("Fetch Error :-S", err);
    });
}

function generateTableHeadGe(table, data, loggedIn) {
  let thead = table.createTHead();
  let row = thead.insertRow();
  for (let key of data) {
    if (key === "id") {
      continue;
    }
    let th = document.createElement("th");
    let text = document.createTextNode(key);
    th.appendChild(text);
    row.appendChild(th);
  }
  let th = document.createElement("th");
  th.className = "btnCol";
  let text = document.createTextNode("");
  th.appendChild(text);
  row.appendChild(th);
  // if (loggedIn) {
  //   let th2 = document.createElement("th");
  //   let text2 = document.createTextNode("Edit");
  //   th2.appendChild(text2);
  //   row.appendChild(th2);

  //   let th3 = document.createElement("th");
  //   let text3 = document.createTextNode("Delete");
  //   th3.appendChild(text3);
  //   row.appendChild(th3);
  // }
}

function generateTableGe(table, GenreData, loggedIn, user) {
  for (let element of GenreData) {
    let row = table.insertRow();
    row.className = "dataRow";
    row.id = "GenreRow"+element.id;
    for (key in element) {
      if (key === "id") {
        continue;
      }
      let cell = row.insertCell();
      cell.id = "Genre"+key+element.id;
      let text = document.createTextNode(element[key]);
      if (key === "albums") {
        let albumNo = 0;
        for (album of element[key]) {
          albumNo += 1;
        }
        text = document.createTextNode(albumNo);
      }
      cell.appendChild(text);
    }
    let newCell = row.insertCell();
    let myViewButton = document.createElement("button");
    myViewButton.className = "btn";
    myViewButton.id = "ViewGenreButton"+element.id;
    let genre = element.id;
    myViewButton.onclick = function () {
      document.location = "Album.html?user=" + user + "&genres=" + genre;
    };

    let viewIcon = document.createElement("span");
    viewIcon.className = "material-icons";
    viewIcon.innerHTML = "launch";
    myViewButton.appendChild(viewIcon);
    newCell.appendChild(myViewButton);

    // if (loggedIn) {
    //   let newCell2 = row.insertCell();
    //   let myEditButton = document.createElement("button");
    //   myEditButton.className = "btn";
    //   myEditButton.id = "EditGenreButton";
    //   myEditButton.setAttribute("data-toggle", "modal");
    //   myEditButton.setAttribute("data-target", "#EditGenreModal");

    //   let editIcon = document.createElement("span");
    //   editIcon.className = "material-icons";
    //   editIcon.innerHTML = "create";
    //   myEditButton.appendChild(editIcon);
    //   let ID = element.id;
    //   let Name = element.name;
    //   myEditButton.onclick = function () {
    //     changeEditGenreModal(ID, Name);
    //   };
    //   newCell2.appendChild(myEditButton);

    //   let newCell3 = row.insertCell();
    //   let myDeleteButton = document.createElement("button");
    //   myDeleteButton.className = "btn";
    //   myDeleteButton.id = "DeleteGenreButton" + element.name;

    //   let deleteIcon = document.createElement("span");
    //   deleteIcon.className = "material-icons";
    //   deleteIcon.innerHTML = "delete";
    //   myDeleteButton.appendChild(deleteIcon);
    //   myDeleteButton.onclick = function () {
    //     deleteGenre(element.id);
    //   };
    //   newCell3.appendChild(myDeleteButton);
    // }
  }
}

function generateAddGenreBtn(table) {
  let tableFooter = document.createElement("footer");
  let myAddGenreButton = document.createElement("button");
  myAddGenreButton.className = "btn btn-outline-primary";
  myAddGenreButton.innerHTML = "Add Genre";
  myAddGenreButton.id = "AddGenreButton";
  myAddGenreButton.setAttribute("data-toggle", "modal");
  myAddGenreButton.setAttribute("data-target", "#AddGenreModal");

  tableFooter.appendChild(myAddGenreButton);
  table.appendChild(tableFooter);
}

function deleteGenre(id) {
  fetch("http://localhost:8082/genres/delete/" + id, {
    method: "delete",
    headers: {
      "Content-type": "application/json",
    },
  })
    // .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      location.reload();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}

let GenreId;

function changeEditGenreModal(id, name) {
  let modalPH = document.getElementById("EditGenreName");
  modalPH.setAttribute("value", name);
  GenreId = id;
}

document
  .querySelector("form.EditGenre")
  .addEventListener("submit", function (stop) {
    stop.preventDefault();

    let formElements = document.querySelector("form.EditGenre").elements;

    let EditGenrename = formElements["EditGenreName"].value;
    let description = formElements["EditGenreDescription"].value;

    editGenre(EditGenrename, GenreId,description);
  });

function editGenre(name, GenreId,description) {
  let ID = parseInt(GenreId);
  fetch("http://localhost:8082/genres/update/" + GenreId, {
    method: "put",
    headers: {
      "Content-type": "application/json",
    },
    body: (json = JSON.stringify({
      "id": ID,
      "name": name,
      "description":description
    })),
  })
    .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      location.reload();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}

document
  .querySelector("form.Genre")
  .addEventListener("submit", function (stop) {
    stop.preventDefault();

    let formElements = document.querySelector("form.Genre").elements;
    let name = formElements["GenreName"].value;
    let description = formElements["GenreDescription"].value;
    addGenre(name,description);
  });

function addGenre(name,description) {
  fetch("http://localhost:8082/genres/create", {
    method: "post",
    headers: {
      "Content-type": "application/json",
    },
    body: (json = JSON.stringify({
      "name": name,
      "description":description
    })),
  })
    .then(json)
    .then(function (data) {
      console.log("Request succeeded with JSON response", data);
      location.reload();
    })
    .catch(function (error) {
      console.log("Request failed", error);
    });
}
