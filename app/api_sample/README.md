# ShelterNavi API一覧
## getShelter
idを基にデータベースから避難所の情報を取得する
- APIをたたく際のendpoint
    - ~/shelters/{shelter_id}
- {shelter_id} には指定したい避難所のidを入れる
- idの入れ方は，ajax等でAPIをたたく際にURLを指定するので，取得したい避難所のidを2028100001とすると，下記のようにすればよい

```js:shelterNavi.js

let endpoint = "~/shelters";
let id = $( '#shelter_id' ).val(); // 中身は2028100001

$.ajax({
    type: "GET",
    url: endpoint + "/" + id,
    dataType: "JSON",
    // 略
})

```

## getAllShelters
すべての避難所の情報を取得する
- APIをたたく際のendpoint
    - ~/shelters
- 下記のように指定することですべての避難所の情報が返ってくる

```js:shelterNavi.js

let endpoint = "~/shelters";

$.ajax({
    type: "GET",
    url: endpoint + "/" + id,
    dataType: "JSON",
    // 略
})

```

## searchSheltersByDistance
ユーザの現在地から半径dkm以内にある避難所の情報を取得する
- APIをたたく際のendpoint
    - ~/shelters/search/distance
- このAPIをたたく際は以下の3つのデータが必要
    - userLng：ユーザの現在地の経度
    - userLat：ユーザの現在地の緯度
    - distance：半径何km以内で避難所を探すか
- 上記の3つはajax等でdataとして指定する必要がある（指定する際の名前は上記の通りに）
- 下記の場合，経度：134.3756，緯度：34.8381 の地点から半径1.0km以内に存在する避難所すべてが返ってくる

```js:shelterNavi.js

let endpoint = "~/shelters";
let data = {
    "userLng": 134.3756, // 経度：134.3756
    "userLat": 34.8381,  // 緯度：34.8381
    "distance": 1.0      // 半径：1.0km
}
$.ajax({
    type: "GET",
    url: endpoint + "/search/distance",
    data: JSON.stringify( data ),
    contentType: "application/json",
    dataType: "JSON",
    // 略
})

```

## searchSheltersByKeyword
キーワード指定によるあいまい検索（部分一致検索）
- APIをたたく際のendpoint
    - ~/shelters/search/keyword
- このAPIをたたく際は以下の1つのデータが必要
    - keyword：避難所の名前，または住所に含まれていそうな文字列
- 上記のデータはajax等でdataとして指定する必要がある（指定する際の名前は上記の通りに）
- 下記のような場合，「城乾」という文字列を名前，または住所のいずれかに含んでいる避難所すべてが返ってくる

```js:shelterNavi.js

let endpoint = "~/shelters";
let data = {
    "keyword": "城乾"
}
$.ajax({
    type: "GET",
    url: endpoint + "/search/distance",
    data: JSON.stringify( data ),
    contentType: "application/json",
    dataType: "JSON",
    // 略
})

```