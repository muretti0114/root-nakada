const serverAddr = getServerAddr();

function getServerAddr() {
    let e = location.href;
    let path = e.substr( 0, e.lastIndexOf( "/" ) );

    return path;
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