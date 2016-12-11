# LOC ＠SBCloud Hackathon 2016
(Live On Cloud Team)

# ソースプログラムのフォルダ
https://github.com/nvquocbao/LOC/tree/master/05_Development/

プログラム構成

* Android スマホアプリ: 

# システム・アプリのインストール／ビルド／テスト方法

### Androidアプリのビルド／インストール
* 開発環境の必要な条件: Android Studio ver2.0 がおすすめである.  
  最新のAndroid SDKのSDKをダウンロードすることは必要である。 
* 下記のプロジェクトをFork and cloneする
https://github.com/nvquocbao/LOC/tree/master/05_Development/Android_App/Santaclaus

#### Android Phoneアプリのビルド／インストール

* Android Studioでプロジェクトを開いてビルドモードを「mobile」を選択する  
* Android 端末の設定を選択し、「開発者向けオプション」を設定する  
* Android Studioで「ビルド」を選択して Android端末でアプリケージョンが起動する

### Webサービスシステム
https://github.com/nvquocbao/LOC/tree/master/05_Development/Web/LOCWS

#### SBCloudにウェブサーバ（ECS）とDBサーバ（RDS）を作成する。

#### ウェブサービスサーバ設定

* ウェブサービスサーバ（ECS）に以下のソフトウェアをインストールする。
	Apache Tomcat 7.0.56

|情報|設定値|
|---|---|
|OS|CentOS|
|Internet IP|47.91.16.210|
|Intranet IP|172.24.26.143|
|EOS name|loc_ecs|
|ユーザアカウント|loc_user|
|パスワード|loc@1210|

### Fileサーバー
https://github.com/nvquocbao/LOC/tree/master/05_Development/Web/avatar

#### SBCloudにウェブサーバ（ECS）を作成する。

#### Fileサーバー設定

* Fileサーバー（ECS）に以下のソフトウェアをインストールする。
	PHP 5.4.16
	Apache (httpd) 2.4.6

|情報|設定値|
|---|---|
|OS|CentOS|
|Internet IP|47.91.16.210|
|Intranet IP|172.24.26.143|
|EOS name|loc_ecs|
|ユーザアカウント|loc_user|
|パスワード|loc@1210|

#### DBサーバー設定

* DBサーバー（RDS）にMySQLをインストールし、ウェブサーバからMySQLに接続できるように設定する。
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

ログインコマンド:
* intranet
mysql -h loc-intranet.mysql.japan.rds.aliyuncs.com -P 3306 -u loc_user -ploc@1210

* internet
mysql -h loc-internet.mysql.japan.rds.aliyuncs.com -P 3306 -u loc_user -ploc@1210

#### 本プロジェクトのデータベースのインストール

* システムのユーザloc_userを作成する。
* データベース作成
```
CREATE DATABASE loc_db
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `birthday` date DEFAULT NULL,
  `avatar_path` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL COMMENT '[0:child,1:parent]',
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `child_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `content` text,
  `create_date` timestamp NULL DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `is_child` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_children` (`child_id`),
  KEY `fk_parent` (`parent_id`),
  CONSTRAINT `fk_children` FOREIGN KEY (`child_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_parent` FOREIGN KEY (`parent_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
```

#### 本プロジェクトのウェブアプリケーションのインストール
「LOC\07_Release\LOCWS.war」をTomcatにデプロイする。

* AndroidアプリからYouGiftマークアイコンを押下して開く。

# 利用するオープンソースライブラリ一覧

|ライブラリ名|用途|配置場所若しくは依存性管理ファイル|URL|
|---|---|---|---|
|Hibernate|For Data Access Object|WebContent\WEB-INF\lib|http://hibernate.org/orm/|
|Jersey|For Rest Web Service|WebContent\WEB-INF\lib|https://jersey.java.net/|
|Jackson|For JSON encode|WebContent\WEB-INF\lib|https://github.com/FasterXML/jackson|


## Reference

Hackathon Project URL: http://www.hackathon.io/projects/13192
