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




```html
<div class="clearfix dropdown ">
      <h2 title="5245 başlık">bugün</h2>
</div>
<div id="feed-info" data-snapshot="66633268" data-deltacount="0"
     data-href="/index/feedrefresh">
  <a id="feed-refresh-link" class="more-data">
    yenile
    <span class="rotator">
      
      <svg class="eksico">
        <use xlink:href="#eksico-cw"></use>
      </svg>
    </span>
  </a>
</div>
<ul class="topic-list partial" data-timestamp="636241782990567897">
  <li>

  <a href="/gezi-olaylarinda-bedava-alkol-dagitildi--5313546">gezi olaylarında bedava alkol dağıtıldı <small>38</small></a>
</li><li>

  <a href="/yeni-tanisilan-bir-kizda-ilk-bakilan-yer--1740675?day=2017-03-03">yeni tanışılan bir kızda ilk bakılan yer <small>75</small></a>
</li><li>

  <a href="/sukela-reader--4717884?day=2017-03-03">şükela reader <small>69</small></a>
</li><li>

  <a href="/unal-aysal--836811?day=2017-03-03">ünal aysal <small>5</small></a>
</li><li>

  <a href="/erkekler-neye-asik-olur--4187557?day=2017-03-03">erkekler neye aşık olur <small>12</small></a>
</li><li>

  <a href="/10-atom-bombasi-gucunde-mega-bomba--5313595">10 atom bombası gücünde mega bomba</a>
</li><li>

  <a href="/payitaht-abdulhamid--5235062?day=2017-03-03">payitaht abdülhamid <small>14</small></a>
</li><li>

  <a href="/seks-kokan-sarkilar--3957536?day=2017-03-03">seks kokan şarkılar <small>6</small></a>
</li><li>

  <a href="/annelerin-sadece-erkek-cocuklarina-verdigi-isler--5313577">annelerin sadece erkek çocuklarına verdiği işler <small>4</small></a>
</li><li>

  <a href="/anderson-vital-da-silva-dede--2588277?day=2017-03-03">anderson vital da silva dede</a>
</li><li>

  <a href="/cayi-sekersiz-icmek--932734?day=2017-03-03">çayı şekersiz içmek</a>
</li><li>

  <a href="/3-mart-2017-155-polis-imdat-rezaleti--5313590">3 mart 2017 155 polis imdat rezaleti <small>4</small></a>
</li><li>

  <a href="/nefret--33680?day=2017-03-03">nefret</a>
</li><li>

  <a href="/almanya--49635?day=2017-03-03">almanya <small>3</small></a>
</li><li>

  <a href="/almanyanin-ibadethane-disinda-namazi-yasaklamasi--5313594">almanya'nın ibadethane dışında namazı yasaklaması <small>79</small></a>
</li><li>

  <a href="/abudik-gubidik-insanlar-basbakan-oluyor--5313425">abudik gubidik insanlar başbakan oluyor <small>24</small></a>
</li><li>

  <a href="/bir-kadinin-en-guzel-bolgesi--125734?day=2017-03-03">bir kadının en güzel bölgesi <small>3</small></a>
</li><li>

  <a href="/alman-mallarini-almiyoruz-kampanyasi--4475309?day=2017-03-03">alman mallarını almıyoruz kampanyası <small>62</small></a>
</li><li>

  <a href="/3-lig-macinda-bilet-fiyatinin-200-tl-olmasi--5313591">3. lig maçında bilet fiyatının 200 tl olması <small>2</small></a>
</li><li>

  <a href="/daydreaming--224937?day=2017-03-03">daydreaming</a>
</li><li>

  <a href="/nocturnal-animals--4735928?day=2017-03-03">nocturnal animals <small>2</small></a>
</li><li>

  <a href="/3-mart-2017-darussafaka-dogus-panathinaikos-maci--5313567">3 mart 2017 darüşşafaka doğuş panathinaikos maçı <small>3</small></a>
</li><li>

  <a href="/hastasi-olunan-sozler--37939?day=2017-03-03">hastası olunan sözler <small>11</small></a>
</li><li>

  <a href="/jbl-go--4942497?day=2017-03-03">jbl go</a>
</li><li>

  <a href="/planet-turk--2573522?day=2017-03-03">planet türk</a>
</li><li>

  <a href="/eksi-sozluk-dertlesecek-insan-veritabani--3188805?day=2017-03-03">ekşi sözlük dertleşecek insan veritabanı <small>32</small></a>
</li><li>

  <a href="/halk-tv--524107?day=2017-03-03">halk tv</a>
</li><li>

  <a href="/survivor-2017--5035836?day=2017-03-03">survivor 2017 <small>60</small></a>
</li><li>

  <a href="/aglayarak-hayir-diyen-ak-partili-teyze--5313285">ağlayarak hayır diyen ak parti'li teyze <small>235</small></a>
</li><li>

  <a href="/kisinin-kiyafetinden-kulturunu-anlayamiyoruz--5313446">kişinin kıyafetinden kültürünü anlayamıyoruz <small>53</small></a>
</li><li>

  <a href="/lubnanda-akillara-zarar-cenaze-merasimi--5312980?day=2017-03-03">lübnan'da akıllara zarar cenaze merasimi <small>11</small></a>
</li><li>

  <a href="/eski-sevgiliyi-hatirlattigindan-dinlenemeyen-sarki--1908891?day=2017-03-03">eski sevgiliyi hatırlattığından dinlenemeyen şarkı <small>3</small></a>
</li><li>

  <a href="/kadin-kokusu--34180?day=2017-03-03">kadın kokusu</a>
</li><li>

  <a href="/sarhosken-entry-girmek--36563?day=2017-03-03">sarhoşken entry girmek</a>
</li><li>

  <a href="/trey-burke--3260180?day=2017-03-03">trey burke</a>
</li><li>

  <a href="/fenerasyon--734074?day=2017-03-03">fenerasyon</a>
</li><li>

  <a href="/whitesnake--37130?day=2017-03-03">whitesnake</a>
</li><li>

  <a href="/reddit--1447362?day=2017-03-03">reddit</a>
</li><li>

  <a href="/tokyoda-8-metrekare-evde-yasayan-kadinin-tasinmasi--5313430">tokyoda 8 metrekare evde yaşayan kadının taşınması <small>15</small></a>
</li><li>

  <a href="/kezofeminist--5305842?day=2017-03-03">kezofeminist</a>
</li><li>

  <a href="/ureddit--5313592">ureddit</a>
</li><li>

  <a href="/cok-kadinla-birlikte-oldum-diyen-erkek--3790805?day=2017-03-03">çok kadınla birlikte oldum diyen erkek <small>42</small></a>
</li><li>

  <a href="/sinan-ogan--2327957?day=2017-03-03">sinan oğan <small>44</small></a>
</li><li>

  <a href="/3-mart-2017-ultraslanin-lisecilere-savas-ilani--5313337">3 mart 2017 ultraslan'ın lisecilere savaş ilanı <small>70</small></a>
</li><li>

  <a href="/vesikali-yarim--147179?day=2017-03-03">vesikalı yarim</a>
</li><li>

  <a href="/duygun-yarsuvat--344244?day=2017-03-03">duygun yarsuvat</a>
</li><li>

  <a href="/22-mart-2017-sony-yarismasinda-ikinci-olmam--5312306?day=2017-03-03">22 mart 2017 sony yarışmasında ikinci olmam <small>6</small></a>
</li><li>

  <a href="/sabire-meltem-banko--5313258?day=2017-03-03">sabire meltem banko <small>279</small></a>
</li><li>

  <a href="/gece-boyu-sevgilisine-sarilarak-uyuyabilen-erkek--3055755?day=2017-03-03">gece boyu sevgilisine sarılarak uyuyabilen erkek <small>79</small></a>
</li><li>

  <a href="/turk-yargisi-alman-yargisindan-daha-bagimsizdir--5313351">türk yargısı alman yargısından daha bağımsızdır <small>97</small></a>
</li></ul>
<div class="quick-index-continue-link-container"><a class="index-link" href="/basliklar/bugun/2" id="quick-index-continue-link">daha da ...</a></div>
```