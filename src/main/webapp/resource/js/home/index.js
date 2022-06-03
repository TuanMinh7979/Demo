var mymap = L.map("map").setView([10.0313860, 105.7694367], 12);
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution: "© OpenStreetMap",
}).addTo(mymap);


let curUrl = window.location.href;
if (curUrl.indexOf("?") != -1) {
    let idx = curUrl.indexOf("?")
    let querystr = curUrl.substring(idx);
    curUrl = curUrl.slice(0, idx)
    curUrl += "api/search/"
    curUrl += querystr;
    console.log(curUrl)
    $.ajax({
        type: "get",
        url: curUrl,
        success: function (data) {
            $("#map").css("display","none")
            let latlngs = [];
            let mynewmap = L.map("newmap").setView([10.0313860, 105.7694367], 12);
            $("#map").css("display","none")
            L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                maxZoom: 19,
                attribution: "© OpenStreetMap",
            }).addTo(mynewmap);
            data.map(function (pi) {
                // let pi= JSON.parse(pointi);
                // const latlongs=L.marker([pi.lat, pi.lon]).addTo(map);
                latlngs.push([parseFloat(pi.lat),parseFloat(pi.lon)])

            })
            console.log(latlngs)

            let polyline = new L.Polyline(latlngs, {
                color: "green",
                weight: 10,
            }).addTo(mynewmap);


        },
        error: function () {
            alert("Error occur")
        }
    });

}


// var myGeoJson = {
//
// };
//
// L.geoJSON(myGeoJson).addTo(map);

