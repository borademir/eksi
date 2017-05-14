[![Build Status](https://travis-ci.org/borademir/eksi.svg?branch=master)](https://travis-ci.org/borademir/eksi)
![Heroku](https://heroku-badge.herokuapp.com/?app=eksici-api&root=api)

Ekşi Sözlük Gayrıresmi API
===================


Ekşi yönetiminin yıllardır yapacağını söylediği ama bir türlü yaparak kullanıma sunmadığı eksi api geliştirme çalışmalarını kapsayacak bir projedir.

----------

Amacımız tarayıcı isteklerini ayıklamak ve günün sonunda tarayıcıdan yapabildiğimiz işlerin programatik olarak yapılabilmesini sağlamaktır. Sonrasında ise bu api üzerinden android/ios/mobil site geliştiriciler için kendi uygulamalarını port edebilmelerini sağlamak ve çeşitli ekşi sözlük fikircilerinin önünü açmaktır.

[şuradaki](TODO.md) sayfadan ayıkladığımız curl komutlarını bulabilrsiniz. 

Proje mavenize edilerek , service katmaninin onune spring mvc katmani eklendi ve rest api ile json veriler dışarı serv edildi.

Burada yapılan tüm geliştirmeler linode vps sunucumuz üzerinden de demo amaçlı paylaşılmaktadır.

> **eksici.com/api endpoint examples**


> - <a href="http://www.eksici.com/api/v1/topic/popular" target="_blank">Populer Entryler</a>
> - <a href="http://www.eksici.com/api/v1/topic/popular?nextPageHref=/basliklar/m/populer?p=2" target="_blank">Populer Entryler ( Sayfa 2 )</a>
> - <a href="http://www.eksici.com/api/v1/topic/today" target="_blank">Günün Entryleri</a>
> - <a href="http://www.eksici.com/api/v1/topic/today?nextPageHref=/basliklar/bugun/2" target="_blank">Günün Entryleri ( Sayfa 2 )</a>
> - <a href="http://www.eksici.com/api/v1/topic/deserted" target="_blank">Başıboşlar</a>
> - <a href="http://www.eksici.com/api/v1/topic/deserted?nextPageHref=/basliklar/basiboslar?p=2" target="_blank">Başıboşlar ( Sayfa 2 )</a>
> - <a href="http://www.eksici.com/api/v1/topic/videos" target="_blank">Videolar</a>
> - <a href="http://www.eksici.com/api/v1/topic/videos?nextPageHref=/basliklar/videolar?p=2" target="_blank">Videolar ( Sayfa 2 )</a>
> - <a href="http://www.eksici.com/api/v1/topic/todayinhistory/2002" target="_blank">Tarihte Bugün ( 2002 )</a>
> - <a href="http://www.eksici.com/api/v1/topic/todayinhistory/2002?nextPageHref=/basliklar/tarihte-bugun?_=1489314304991&year=2002&p=2" target="_blank">Tarihte Bugün ( 2002 ) ( Sayfa 2 )</a>
> - <a href="http://www.eksici.com/api/v1/channels" target="_blank">Kanallar</a>
> - <a href="http://www.eksici.com/api/v1//channels/topics?topicsHref=/basliklar/kanal/spor" target="_blank">Spor Kanalına Ait Başlıklar</a>
> - <a href="http://www.eksici.com/api/v1/channels/topics?topicsHref=/basliklar/kanal/ili%C5%9Fkiler" target="_blank">İlişkiler Kanalına Ait Başlıklar</a>
> - <a href="http://www.eksici.com/api/v1/channels/topics?topicsHref=/basliklar/kanal/ili%C5%9Fkiler?p=2" target="_blank">İlişkiler Kanalına Ait Başlıklar ( Sayfa 2 )</a>
> - <a href="http://www.eksici.com/api/v1/topics/entries?topicsHref=/turkiyeden-siktir-olup-gitmek--3843083?nr=true&rf=bu%20ülkeden%20siktir%20olup%20gitmek" target="_blank">(türkiye'den siktir olup gitmek) başlığındaki entryler( Sayfa sayfa )</a>
> - <a href="http://www.eksici.com/api/v1/topics/entries?topicsHref=/portakal-bicaklayarak-hollandayi-protesto-etmek--5319445?a=popular" target="_blank">portakal-bicaklayarak-hollandayi-protesto-etmek</a>
> - <a href="http://www.eksici.com/api/v1/topics/entries?topicsHref=/portakal-bicaklayarak-hollandayi-protesto-etmek--5319445?a=popular&p=2" target="_blank">portakal-bicaklayarak-hollandayi-protesto-etmek ( Sayfa 2 )</a>
> - <a href="http://www.eksici.com/api/v1/entry/66802288" target="_blank">Entry</a>
> - <a href="http://www.eksici.com/api/v1/topic/search?author=qlluq&keyword=aykut" target="_blank">Detayli Arama</a>
> - <a href="http://www.eksici.com/api/v1/autocomplete?query=tuna" target="_blank">Autocomplete Arama</a>






#### Rest WAR oluşturmak için:

```
mvn clean install
```

Devam edeceğiz..