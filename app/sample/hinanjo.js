/* hinanjo.js
 * 避難所をGoogle Mapsに表示するためのJavaScriptサンプル
 */
var map;
var marker = {};
var infoWindow = {};

//とりあえず姫路城を中心に
var center = {
    lat: 34.8394324,
    lng: 134.693894
};

//プロトタイプ用避難所リスト (本番環境ではAPIから取得する)
var hinanjoList = {
    2820100115: {
        name: "城乾小学校",
        address: "南八代町6-60",
        lng: 134.6867863,
        lat: 34.84348524,
        size: 380,
        capacity: 19
    },
    2820100116: {
        name: "城乾中学校",
        address: "南八代町6-1",
        lng: 134.6860274,
        lat: 34.84252234,
        size: 495,
        capacity: 25
    },
    2820100117: {
        name: "城西小学校",
        address: "新在家二丁目4-1",
        lng: 134.6815987,
        lat: 34.844843,
        size: 420,
        capacity: 21
    },
    2820100118: {
        name: "城東公民館",
        address: "城東町竹之門15",
        lng: 134.7052226,
        lat: 34.83709037,
        size: 110,
        capacity: 6
    },
    2820100130: {
        name: "城の西公民館",
        address: "岡町33",
        lng: 134.6848486,
        lat: 34.84026359,
        size: 110,
        capacity: 6
    },

    2820100112: {
        name: "市民会館",
        address: "総社本町112",
        lng: 134.6955674,
        lat: 34.83282856,
        size: 12334,
        capacity: 616,
    },
    2820100113: {
        name: "生涯学習大学校",
        address: "田寺東二丁目11-1",
        lng: 134.674974,
        lat: 34.85602258,
        size: 4474,
        capacity: 223.7
    },
    2820100114: {
        name: "城乾市民センター",
        address: "南八代町6-1",
        lng: 134.6869033,
        lat: 34.84270933,
        size: 606,
        capacity: 30
    },
}


// 地図の初期化
function initMap() {
    var latlng = new google.maps.LatLng(center.lat, center.lng);
    map = new google.maps.Map(document.getElementById('gmap'), {
        zoom: 14,
        center: center
    });
    addHinanjo();
}


//避難所の追加
function addHinanjo() {
    for (var id in hinanjoList) {
        //避難所リストから1つ取り出してhとする
        var h = hinanjoList[id];
        //避難所の緯度・経度
        var pos = { lat: h.lat, lng: h.lng };
        //h用のマーカーを作成
        marker[id] = new google.maps.Marker({ // マーカーの追加
            position: pos,
            map: map, 
            title: h.name
        });
        //h用の情報ウィンドウ（吹き出し）を作成
        infoWindow[id] = new google.maps.InfoWindow({ // 吹き出しの追加
            content: '<div class="info">' + h.name + ' ('+ h.address + ') ' + '</div>' // 吹き出しに表示する内容
        });
        //マーカーとウィンドウのバインド（別関数でやらないとダメ．クリック時のidが変わるので）
        bind_info(id);
    }
}

//マーカークリック時に情報ウィンドウを開く
function bind_info(id) {
    marker[id].addListener('click', function() { // マーカーをクリックしたとき
        infoWindow[id].open(map, marker[id]); // 吹き出しの表示
    });
}

function test() {
    navigator.geolocation.getCurrentPosition(test2);
}

function test2(position) {

    var geo_text = "緯度:" + position.coords.latitude + "\n";
    geo_text += "経度:" + position.coords.longitude + "\n";
    geo_text += "高度:" + position.coords.altitude + "\n";
    geo_text += "位置精度:" + position.coords.accuracy + "\n";
    geo_text += "高度精度:" + position.coords.altitudeAccuracy  + "\n";
    geo_text += "移動方向:" + position.coords.heading + "\n";
    geo_text += "速度:" + position.coords.speed + "\n";

    var date = new Date(position.timestamp);

    geo_text += "取得時刻:" + date.toLocaleString() + "\n";

    alert(geo_text);

}
