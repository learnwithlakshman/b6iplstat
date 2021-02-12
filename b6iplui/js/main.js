idLabels = document.querySelector("#idLabels");
idPlayers = document.querySelector("#idPlayers");
idPiechart = document.querySelector("#idPiechart");
baseUrl = "http://localhost:8080/iplstat/api/"
idTableChart = document.querySelector("#idTableChart");
function getLabels() {

    fetch(`${baseUrl}ipl/labels`).then(resp => resp.json()).then(data => {
        labels = data["labels"];
        str = ` <div class="form-group">
        <select class="form-control" id="idSelect" onchange="showPLayers()">
        <option value=''>Select Team Label</option>`
        for (i = 0; i < labels.length; i++) {
            str += `<option value=${labels[i]}>${labels[i]}</option>`
        }
        str += `       
        </select>
      </div>`


        idLabels.innerHTML = str;
    })



}

var formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'INR',
});
function showPLayers() {
    idTableChart.innerHTML = "";
    teamName = document.querySelector("#idSelect").value;
    str = `<table class="table table-striped table-sm">    
        <thead><tr><th>#</th><th>Name</th><th>Role</th><th>Label</th><th>Price</th></tr></thead>
        <tbody>`;
    if (teamName != "") {
        fetch(`${baseUrl}ipl/players/${teamName}`).then(json => json.json()).then(res => {
            players = res;
            for (i = 0; i < players.length; i++) {
                player = players[i];
                str += `<tr><td>${i + 1}</td><td>${player['name']}
                </td><td>${player['role']}</td><td>${player['label']}</td><td>${formatter.format(player['price'])}</td></tr>`;
            }
            str += `</tbody></table>`;
            idPlayers.innerHTML = str;
        })
    } else {
        str = "";
        idPlayers.innerHTML = str;
    }

    fetch(`${baseUrl}ipl/rolecount/${teamName}`).then(json => json.json()).then(res => {
        showPieChart(res, teamName);
    })
}

function showPieChart(res, teamName) {
    google.charts.load('current', { 'packages': ['corechart'] });
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        headings = ["Role", "Count"];
        arrData = [];
        arrData.push(headings);
        res.forEach(ele => {
            arrData.push([ele["roleName"], ele["count"]])
        })
        var data = google.visualization.arrayToDataTable(arrData);

        var options = {
            title: `Players count by role (${teamName})`
        };

        var chart = new google.visualization.PieChart(idPiechart);

        chart.draw(data, options);
        google.visualization.events.addListener(chart, 'select', function (e) {
            roleName = arrData[chart.getSelection()[0]['row'] + 1][0];
            showTableChart(roleName, teamName);
        });

    }

}

function showTableChart(roleName, teamName) {
    console.log(roleName)
    str = "";
    str = `
    <div class='py-2 text-center text-white bg-primary'>${teamName} :  ${roleName}</div>
    <table class="table table-striped table-sm">    
    <thead><tr><th>#</th><th>Name</th><th>Role</th><th>Label</th><th>Price</th></tr></thead>
    <tbody>`;
    if (teamName != "") {
        fetch(`${baseUrl}ipl/players/${teamName}/${roleName}`).then(json => json.json()).then(res => {
            players = res;
            for (i = 0; i < players.length; i++) {
                player = players[i];
                str += `<tr><td>${i + 1}</td><td>${player['name']}
                    </td><td>${player['role']}</td><td>${player['label']}</td><td>${formatter.format(player['price'])}</td></tr>`;
            }
            str += `</tbody></table>`;
            idTableChart.innerHTML = str;
        })
    } else {
        str = "";
        idTableChart.innerHTML = str;
    }


}

getLabels();