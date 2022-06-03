var map = L.map("map").setView([10.0313860, 105.7694367], 13);
L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution: "© OpenStreetMap",
}).addTo(map);


// var myGeoJson = {
//
// };
//
// L.geoJSON(myGeoJson).addTo(map);

