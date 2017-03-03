Ekşi Sözlük Gayrıresmi API
===================


Ekşi yönetiminin yıllardır yapacağını söylediği ama bir türlü yaparak kullanıma sunmadığı eksi api geliştirme çalışmalarını kapsayacak bir projedir.

----------

Amacımız tarayıcı isteklerini ayıklamak ve günün sonunda tarayıcıdan yapabildiğimiz işlerin programatik olarak yapılabilmesini sağlamaktır. Sonrasında ise bu api üzerinden android/ios/mobil site geliştiriciler için kendi uygulamalarını port edebilmelerini sağlamak ve çeşitli ekşi sözlük fikircilerinin önünü açmaktır.

> **TODO List:**

> - Google chrome üzerinden siteyi dolaşmaya başlayacağız.
> - API içerisinde olursa güzel olur diyeceğimiz hareketlerin nasıl gerçekleştiğini , chrome developer tools ile sniff etmeye çalışacağız.
> - Kendi geliştirme ortamımızı, curl komutlarını çalıştırabilir şekilde özelleştireceğiz.
> - Developer tools içerisinden ilgili isteğin curl formatını kopyalayarak , denemeler yapacağız ve minimum request headerlar ile çalışabilir formatları aşağıda listeleyeceğiz.
> - Sonrasında da şu an için java platformuna bu işleri curl ortamdan port edeceğiz, başka platformlardan da destek alındıkça buralarda yayınlayacağız.

Başlayalım o zaman!
-------------



#### Populer Başlıklar:

```
curl "https://eksisozluk.com/basliklar/m/populer" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" -H "Referer: https://eksisozluk.com/" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6"
```

#### Bugün:

```
curl "https://eksisozluk.com/index/feedrefresh?_=1488568053748" -H "Accept: */*" -H "X-Requested-With: XMLHttpRequest" -H "Referer: https://eksisozluk.com/" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6"
```

#### Başıboşlar:

```
curl "https://eksisozluk.com/basliklar/basiboslar?_=1488571362708" -H "Accept: */*" -H "X-Requested-With: XMLHttpRequest"  -H "Referer: https://eksisozluk.com/" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6"
```

#### Videolar:

```
curl "https://eksisozluk.com/basliklar/videolar?_=1488571362709" -H "Accept: */*" -H "X-Requested-With: XMLHttpRequest" -H "Referer: https://eksisozluk.com/"  -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6"
```

#### Tarihte Bugün:

```
curl "https://eksisozluk.com/basliklar/tarihte-bugun?_=1488571362710" -H "Accept: */*" -H "X-Requested-With: XMLHttpRequest" -H "Referer: https://eksisozluk.com/" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6"
```

#### Tarihte Bugün: ( XXXX yılında)

```
curl "https://eksisozluk.com/basliklar/tarihte-bugun?year=2010&_=1488571362711" -H "Accept: */*" -H "X-Requested-With: XMLHttpRequest" -H "Referer: https://eksisozluk.com/" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6"
```


> **Note:**

> Paging özelliği olan adreslere "p" parametresi ile sayfa numarası geçirilerek yeni veriler istenebilir ( örnek &p=2, &p=3)


#### Başlık ararken yapılan öneriler:

```
curl "https://eksisozluk.com/autocomplete/query?q=fenerb^&_=1488571362714" -H "Accept: application/json, text/javascript, */*; q=0.01" -H "Referer: https://eksisozluk.com/" -H "X-Requested-With: XMLHttpRequest"
```

#### Kanala ait başlıklar ( örnek : spor ) :

```
curl "https://eksisozluk.com/basliklar/kanal/spor?_=1488571362717" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6" -H "Accept: */*" -H "Referer: https://eksisozluk.com/" -H "X-Requested-With: XMLHttpRequest" -H "Connection: keep-alive" --compressed
```

#### Başlıktaki günün entryleri :

```
curl "https://eksisozluk.com/jeremain-lens--1935794?day=2017-03-03" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6" -H "Upgrade-Insecure-Requests: 1" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" -H "Connection: keep-alive" --compressed
```

#### Başlıktaki günün şükelaları :

```
curl "https://eksisozluk.com/jeremain-lens--1935794?a=dailynice" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6" -H "Upgrade-Insecure-Requests: 1" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" -H "Connection: keep-alive" --compressed
```

#### Başlıktaki tüm şükelalar :

```
curl "https://eksisozluk.com/jeremain-lens--1935794?a=nice" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6" -H "Upgrade-Insecure-Requests: 1" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8" -H "Connection: keep-alive" --compressed
```


Count / Date / Topic
curl "https://eksisozluk.com/basliklar/ara?SearchForm.Keywords=fener^&SearchForm.Author=yol^%^20yordam^%^20ogren^%^20oyle^%^20gel^&SearchForm.When.From=2017-01-02^&SearchForm.When.To=2017-03-02^&SearchForm.NiceOnly=true^&SearchForm.NiceOnly=false^&SearchForm.SortOrder=Topic^&_=1488574253486" -H "Accept-Encoding: gzip, deflate, sdch, br" -H "Accept-Language: en-US,en;q=0.8,tr;q=0.6" -H "Accept: */*" -H "Referer: https://eksisozluk.com/?q=" -H "X-Requested-With: XMLHttpRequest" -H "Connection: keep-alive" --compressed