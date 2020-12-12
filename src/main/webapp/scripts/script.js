window.addEventListener('DOMContentLoaded', getData);

const filter = document.querySelector('#filter');
const add = document.querySelector('.add');
const todo = document.querySelector('.todo');
let allTasks = filter.checked;
let textTask = "";
let idTask = -1;

function getData() {
    let cat = getCategory();

    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/todo/getList',
        data: {
            allTasks: allTasks,
            addTask: textTask,
            changeTask: idTask,
            category: cat
        }
    }).done(function (data) {
        const d = JSON.parse(data);
        $('.todo').empty().html(d.items);
        $('.auth').empty().html(d.login);
        $('.category').empty().html(d.category);
    }).fail(function (err) {
        alert(err);
    });
    idTask = -1;
    textTask = "";
}

filter.addEventListener('click', () => {
    allTasks = !allTasks;
    getData();
});

add.addEventListener('click', () => {
    textTask = document.querySelector('.message').value;
    getData();
    document.querySelector('.message').value = "";
});

todo.addEventListener('change', (event) => {
    const id = event.target.getAttribute('id');
    let index = id.indexOf('_');
    idTask = id.substring(index + 1, id.length);
    getData();
});

function getCategory() {
    const selected = document.querySelectorAll('.category option:checked');
    return Array.from(selected).map(el => el.value);
}
