const serverAddr = getServerAddr();

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
    let endpoint = serverAddr + "/admin/shelters/";

    $( '#shelters td' ).remove();

    $.ajax({
        type: "GET",
        url: endpoint,
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
    let endpoint = serverAddr + "/shelters/";
    let id = $( '#shelter_id' ).val();

    $( '#shelter td' ).remove();

    $.ajax({
        type: "GET",
        url: endpoint + "/" + id,
        dataType: "JSON",
    }).done( function ( data, dataType ) {
        let shelter = data.result;
        
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

        $( '#shelter' ).append( '<tr><td>' + id + '</td><td>' + name + '</td><td>' + address + '</td><td>' + lng + '</td><td>' + lat + '</td><td>' + area + '</td><td>' + maxFamilies + '</td><td>' + contactPhone + '</td><td>' + note + '</td><td>' + localGovernmentCode + '</td></tr>' );
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