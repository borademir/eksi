Ekşi Sözlük Gayrıresmi API
===================


Ekşi yönetiminin yıllardır yapacağını söylediği ama bir türlü yaparak kullanıma sunmadığı eksi api geliştirme çalışmalarını kapsayacak bir projedir.

----------

Amacımız tarayıcı isteklerini ayıklamak ve günün sonunda tarayıcıdan yapabildiğimiz işlerin programatik olarak yapılabilmesini sağlamaktır. Sonrasında ise bu api üzerinden android/ios/mobil site geliştiriciler için kendi uygulamalarını port edebilmelerini sağlamak ve çeşitli ekşi sözlük fikircilerinin önünü açmaktır.

[şuradaki](TODO.md) sayfadan ayıkladığımız curl komutlarını bulabilrsiniz. 

Proje mavenize edilerek , service katmaninin onune spring mvc katmani eklendi ve rest api ile json veriler dışarı serv edildi.

Burada yapılan tüm geliştirmeler linode vps sunucumuz üzerinden de demo amaçlı paylaşılmaktadır.

#### http://www.eksici.com/api/v1/topic/popular
#### http://www.eksici.com/api/v1/topic/popular?nextPageHref=/basliklar/m/populer?p=2

#### http://www.eksici.com/api/v1/topic/today
#### http://www.eksici.com/api/v1/topic/today?nextPageHref=/basliklar/bugun/2

http://www.eksici.com/api/v1/topic/deserted
http://www.eksici.com/api/v1/topic/deserted?nextPageHref=/basliklar/basiboslar?p=2

http://www.eksici.com/api/v1/topic/videos
http://www.eksici.com/api/v1/topic/videos?nextPageHref=/basliklar/videolar?p=2

http://www.eksici.com/api/v1/topic/todayinhistory/2002
http://www.eksici.com/api/v1/topic/todayinhistory/2002?nextPageHref=/basliklar/tarihte-bugun?_=1489314304991&year=2002&p=2


http://www.eksici.com/api/v1/channels
http://www.eksici.com/api/v1//channels/topics?topicsHref=/basliklar/kanal/spor

http://www.eksici.com/api/v1/channels/topics?topicsHref=/basliklar/kanal/ili%C5%9Fkiler
http://www.eksici.com/api/v1/channels/topics?topicsHref=/basliklar/kanal/ili%C5%9Fkiler?p=2


http://www.eksici.com/api/v1/topics/entries?topicsHref=/escinsel-oldugunu-sozlukte-dile-getiren-ahlaksiz--3292470?day=2017-03-12

http://www.eksici.com/api/v1/topics/entries?topicsHref=/portakal-bicaklayarak-hollandayi-protesto-etmek--5319445?a=popular
http://www.eksici.com/api/v1/topics/entries?topicsHref=/portakal-bicaklayarak-hollandayi-protesto-etmek--5319445?a=popular&p=2

http://www.eksici.com/api/v1/entry/66802288


#### Rest WAR oluşturmak için:

```
mvn clean install
```

Devam edeceğiz..