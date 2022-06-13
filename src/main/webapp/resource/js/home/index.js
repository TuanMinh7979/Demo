let curUrl = window.location.href;
if (curUrl.indexOf("?") == -1) {
    let mymap = L.map("map").setView([10.0477147, 105.7869646], 15);
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 19,
        attribution: "© OpenStreetMap",
    }).addTo(mymap);
} else {
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
            if (data.length == 0) {
                alert("Khong co loi di");
                window.location.href = "/";
            } else {
                let latlngs = [];
                let mynewmap = L.map("map").setView([10.0477147, 105.7869646], 15);

                L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                    maxZoom: 19,
                    attribution: "© OpenStreetMap",
                }).addTo(mynewmap);


                data.map(function (pi) {
                    // let pi= JSON.parse(pointi);
                    // const latlongs=L.marker([pi.lat, pi.lon]).addTo(map);
                    latlngs.push([parseFloat(pi.lat), parseFloat(pi.lon)])

                })


                let polyline = new L.Polyline(latlngs, {
                    color: "green",
                    weight: 6,
                }).addTo(mynewmap);

            }

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

