# LOC ＠SBCloud Hackathon 2016
(Live On Cloud Team)

# ソースプログラムのフォルダ
https://github.com/nvquocbao/LOC/tree/master/05_Development/

プログラム構成

* Android スマホアプリ: 

# システム・アプリのインストール／ビルド／テスト方法

### Androidアプリのビルド／インストール
* 開発環境の必要な条件: Android Studio ver2.0 がおすすめです.  
  最新のAndroid SDKのSDKをダウンロードすることは必要です。 
* 下記のプロジェクトをFork and cloneする
https://github.com/nvquocbao/LOC/tree/master/05_Development/Android_App/Santaclaus

#### Android Phoneアプリのビルド／インストール

* Android Studioでプロジェクトを開いてビルドモードを「mobile」を選択する  
* Android 端末の設定を選択し、「開発者向けオプション」を設定する  
* Android Studioで「ビルド」を選択して Android端末でアプリケージョンが起動する

### Webサービスシステム
https://github.com/nvquocbao/LOC/tree/master/05_Development/Web/LOCWS

#### SBCloudにウェブサーバ（ECS）とDBサーバ（RDS）を作成する。

#### ウェブサーバ設定

* ウェブサーバ（ECS）に以下のソフトウェアをインストールする。
```sh
```
|情報|設定値|
|---|---|
|OS|CentOS|
|Internet IP|47.91.16.210|
|Intranet IP|172.24.26.143|
|EOS name|loc_ecs|
|ユーザアカウント|loc_user|
|パスワード|loc@1210|

#### DBサーバ設定

* DBサーバ（RDS）にMySQLをインストールし、ウェブサーバからMySQLに接続できるように設定する。
データベースとユーザを作成する。

|情報|設定値|
|---|---|
|DBタイプ|MySQL 5.6|
|DB名|loc_db|
|ユーザアカウント|loc_user|
|パスワード|loc@1210|
|Port|3306|
|access address|loc-intranet.mysql.japan.rds.aliyuncs.com|
|access address|loc-internet.mysql.japan.rds.aliyuncs.com|

to login:
* intranet
mysql -h loc-intranet.mysql.japan.rds.aliyuncs.com -P 3306 -u loc_user -ploc@1210

* internet
mysql -h loc-internet.mysql.japan.rds.aliyuncs.com -P 3306 -u loc_user -ploc@1210

#### 本プロジェクトのウェブアプリケーションのインストール

* システムのユーザloc_userを作成する。
* 
```

```

#### 初期データ設定

ウェブサーバ上に以下のシェルコマンド実行
```

```
#### テスト

* AndroidアプリからYouGiftマークアイコンを押下して開く。

# 利用するオープンソースライブラリ一覧

|ライブラリ名|用途|配置場所若しくは依存性管理ファイル|URL|
|---|---|---|---|
|Hibernate|For Data Access Object|WebContent\WEB-INF\lib|http://hibernate.org/orm/|
|Jersey|For Rest Web Service|WebContent\WEB-INF\lib|https://jersey.java.net/|
|Jackson|For JSON encode|WebContent\WEB-INF\lib|https://github.com/FasterXML/jackson|


## Reference

Hackathon Project URL: http://www.hackathon.io/projects/13192
