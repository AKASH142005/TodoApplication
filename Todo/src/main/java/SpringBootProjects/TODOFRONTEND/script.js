const SERVER_URL = "http://localhost:8080";
const token = localStorage.getItem("token");

function login(){
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/login` ,{
        method : "POST",
        headers : {"Content-Type" :"application/json"},
        body : JSON.stringify({email,password})
    }).then(response => {
        if(!response.ok){
            throw new Error(response.message || "Login failed")
        }
        return response.json();
    }).then(data =>{
        localStorage.setItem("token" , data.token);
        window.location.href = "Todos.html"
    }) .catch (error => {
        alert(error.message)
    })
}

function register(){
    const email = document.getElementById("email").value ;
    const password = document.getElementById("password").value ;

    fetch(`${SERVER_URL}/auth/register` ,{
          method: "POST",
          headers :{"Content-Type" :"application/json" } ,
          body : JSON.stringify({email,password})
     })
     .then(response =>{
        if(response.ok){
            alert("Registration SuccessFull , Please Login!");
            window.location.href="login.html"
        } else {
            return response.json()
            .then(data => {throw new Error(data.message || "Registration failed")});
        }
     }).catch(error =>{
        alert(error.message);
     })


}

function createTodoCard(todo){
    const card = document.createElement("div");
    card.className = "todo-card ";
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox"
    checkbox.checked = todo.completed;
    checkbox.addEventListener("change" ,function () {
        const updatedTodo = {...todo , completed : checkbox.checked}
        updateTodoStatus(updatedTodo);
    });

    const span = document.createElement("span");
    span.textContent = todo.title ;

    if(todo.completed){
        span.style.textDecoration = "line-through";
        span.style.color = "#aaa";
    }

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "X";
    deleteBtn.onclick = () => deleteTodo(todo.id);

    card.appendChild(checkbox);
    card.appendChild(span);
    card.appendChild(deleteBtn);
    return card
}

function loadTodos(){
    if(!token) {
        alert("Please login first")
        window.location.href ="login.html"
        return ;
    }

    fetch (`${SERVER_URL}/api/todo` , {
        method :"GET" ,
        headers : {
            Authorization :`Bearer ${token}`
        }
    }) 
    .then (response =>{
        if(!response.ok){
            throw new Error ( "Failed to get todos");
        }
        return response.json() ;
    }).then ((todos) =>{
        const todoList = document.getElementById("todo-list");
        todoList.innerHTML = ""
        if(!todos || todos.length === 0){
            todoList.innerHTML = `<p id="empty-message">No Todos Yet Add one Todo</p>`
        } else {
            todos.forEach(todo => {
                todoList.appendChild(createTodoCard(todo));
            });
        }
    }) .catch (error => {
        alert(error.message);
        document.getElementById("todo-list").innerHTML =`<p style="color:red">Failed to load todos</p>`
    })
}

function addTodo(){
    const input = document.getElementById("new-todo");
    const todoText = input.value.trim();

    if(!todoText){
        alert("Todo cannot be empty");
        return;
    }

    fetch(`${SERVER_URL}/api/todo/create`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({ title: todoText, isCompleted: false })
    })
    .then(response => {
        if(!response.ok){
            const data = response.json();
            throw new Error(data.message || "Failed to add todo");
        }
        return response.json();
    })
    .then(() => {
        input.value = "";
        loadTodos();
    })
    .catch(error => alert(error.message));
}


function updateTodoStatus(todo){
    fetch(`${SERVER_URL}/api/todo` , {
        method : "PUT",
        headers : {
            "Content-Type" :"application/json",
            "Authorization" : `Bearer ${token}`,
        } ,
           body:JSON.stringify(todo)
    }) .then(response =>{
        if(!response.ok){
            throw new Error("Failed to Update todo")
        }
        return response.json();
    }) .then(() => loadTodos())
    .catch(error=>{
        alert(error.message);
    })
}

function deleteTodo(id){
    fetch(`${SERVER_URL}/api/todo/${id}` ,{
        method :"DELETE" ,
        headers : {
            "Authorization" : `Bearer ${token}`
        }
    })
    .then(response =>{
        if(!response.ok){
            throw new Error("Failed to delete todo");
        }
        return response.text();
    }) 
    .then(() => {
        loadTodos();
    })
    .catch (error =>{
        alert(error.message);
    })
}

document.addEventListener("DOMContentLoaded" , function () {
    if(document.getElementById("todo-list")){
        loadTodos();
    }
});