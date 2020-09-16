const serverAddr = getServerAddr();
var lat;
var lng;
var shelter={

};
var marker = {

};
var infoWindow = {

};



function initMap() {}

function getServerAddr() {
    let e = location.href;
    let path = e.substr( 0, e.lastIndexOf( "/" ) );

    return path;
}

function createShelter() {
    let endpoint = serverAddr + "/admin/shelters/create/";

    let id = $( '#create_id' ).val();
    let name = $( '#create_name' ).val();
    let address = $( '#create_address' ).val();
    let lng = $( '#create_lng' ).val();
    let lat = $( '#create_lat' ).val();
    let area = $( '#create_area' ).val();
    let max_families = $( '#create_max_families' ).val();
    let contact_phone = $( '#create_contact_phone' ).val();
    let note = $( '#create_note' ).val();
    let local_government_code = $( '#create_local_government_code' ).val();

    let data = {
        "id": id,
        "name": name,
        "address": address,
        "lng": lng,
        "lat": lat,
        "area": area,
        "max_families": max_families,
        "contact_phone": contact_phone,
        "note": note,
        "local_government_code": local_government_code
    };

    /* 
        contentTypeなし，JSON.stringfyあり -> 415
        contentTypeあり，JSON.stringfyなし -> 400
        どっちもなし -> 415
        このデータの渡し方だとnoteとかcontact_phoneにnullではなく，""が渡される
    */
    $.ajax({
        type: "POST",
        url: endpoint,
        data: JSON.stringify( data ),
        /*{
            id: id,
            name: name,
            address: address,
            lng: lng,
            lat: lat,
            area: area,
            max_families: max_families,
            contact_phone: contact_phone,
            note: note,
            local_government_code: local_government_code
        },
        */
        contentType: 'application/json',
        dataType: "JSON"
    }).done( function ( data, dataType ) {
        showMessage( "避難所：" + $( '#create_id' ).val() + " を作成しました" );
        clearCreateForm();
    }).fail( function ( xhr ) {
        let res = $.parseJSON( xhr.responseText );

        console.log( res );

        switch ( res.code ) {
            case "E10":
                showMessage( "避難所ID：" + $( '#create_id' ).val() + " は既に存在します" );
                break;
            
            case "E11":
                showMessage( "入力項目に誤りがあります" );
                break;
        }
    })
}

function getAllShelters() {
    let endpoint = "https://wsapp.cs.kobe-u.ac.jp/shelter_navi/shelters";
    let id = $( '#shelter_id' ).val();
    $( '#shelters td' ).remove();

    $.ajax({
        type: "GET",
        url: endpoint + "/" + id,
        dataType: "JSON"
    }).done( function ( data, dataType ) {
        $.each( data.result, function ( index, shelter ) {
            let id = shelter.id;
            let name = shelter.name;
            let address = shelter.address;
            let lng = shelter.lng;
            let lat = shelter.lat;
            let area = shelter.area;
            let maxFamilies = shelter.max_families;
            let contactPhone = shelter.contact_phone;
            let note = shelter.note;
            let localGovernmentCode = shelter.local_government_code;

            $( '#shelters' ).append( '<tr><td>' + id + '</td><td>' + name + '</td><td>' + address + '</td><td>' + lng + '</td><td>' + lat + '</td><td>' + area + '</td><td>' + maxFamilies + '</td><td>' + contactPhone + '</td><td>' + note + '</td><td>' + localGovernmentCode + '</td></tr>' );
        
        
        });
    }).fail( function ( xhr ) {
        console.log( xhr );
        showMessage( "避難所が存在しません" );
    });
}

function getShelters() {
    let endpoint = "https://wsapp.cs.kobe-u.ac.jp/shelter_navi/shelters";
    let id = $( '#shelter_id' ).val();

    $( 'result table' ).remove();

    $.ajax({
        type: "GET",
        url: endpoint + "/" + id,
        dataType: "JSON",
    }).done( function ( data, dataType ) {
        shelter = data.result;
        
        let id = shelter.id;
        let name = shelter.name;
        let address = shelter.address;
        lng = shelter.lng;
        lat = shelter.lat;
        let area = shelter.area;
        let maxFamilies = shelter.max_families;
        let contactPhone = shelter.contact_phone;
        let note = shelter.note;
        let localGovernmentCode = shelter.local_government_code;

       
        $('result').append( '<table><tr><th scope="col">避難所ID</th><th scope="col">施設名</th><th scope="col">住所</th><th scope="col">経度</th><th scope="col">緯度</th><th scope="col">地域</th><th scope="col">収容人数</th><th scope="col">電話番号</th><th scope="col">備考</th><th scope="col">地方自治体コード</th></tr><tr><td>' + id + '</td><td>' + name + '</td><td>' + address + '</td><td>' + lng + '</td><td>' + lat + '</td><td>' + area + '</td><td>' + maxFamilies + '</td><td>' + contactPhone + '</td><td>' + note + '</td><td>' + localGovernmentCode + '</td></tr></table>' );
        
        initMap()

    }).fail( function ( xhr ) {
        console.log( xhr );
        showMessage( "避難所が存在しません" );
    });

   
}

function showMessage( message ) {
    $( '#resultMessage' ).text( message );
}

function clearCreateForm() {
    $( '#create_id' ).val( "" );
    $( '#create_name' ).val( "" );
    $( '#crate_address' ).val( "" );
    $( '#create_lng' ).val( "" );
    $( '#create_lat' ).val( "" );
    $( '#create_area' ).val( "" );
    $( '#create_max_families' ).val( "" );
    $( '#create_contact_phone' ).val( "" );
    $( '#create_note' ).val( "" );
    $( '#create_local_government_code' ).val( "" );
}

function initMap() {
  
    var center = {
        lat: +34.8394324,
        lng: +134.693894,
    };
  
    
    var latlng = new google.maps.LatLng(parseFloat(center.lat),parseFloat(center.lng) );
    var myOptions =  {
        zoom: 14,
        center: latlng,
    };
    map = new google.maps.Map(document.getElementById('map'),myOptions);
    alert(latlng);
    addHinanjo();
}
//避難所の追加
function addHinanjo() {
    for (var id in shelter) {
        //避難所リストから1つ取り出してhとする
        var h = shelter[id];
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



function f(){
    getShelters()
    
    
}