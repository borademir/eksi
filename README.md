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
curl "https://eksisozluk.com/basliklar/m/populer" -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"
```

#### Bugün:

```
curl "https://eksisozluk.com/index/feedrefresh?_=1488568053748" -H "Accept: */*" -H "X-Requested-With: XMLHttpRequest" 
```