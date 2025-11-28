# MiniAdventure – Metin Tabanlı Mini Macera Oyunu (Java, Konsol)

Bu proje, Java ile geliştirilmiş konsol tabanlı bir **mini macera oyunu**dur.  
Oyuncu, birbirine bağlı odalardan oluşan küçük bir dünyada dolaşır; odalar arasında geçiş yapar, eşyaları toplar ve kullanır, dost ve düşman NPC’lerle etkileşime girer.

Projenin ana amacı, **nesne tabanlı programlama (OOP)** kavramlarını bir oyun senaryosu üzerinden uygulamalı olarak göstermektir:

- Kalıtım (inheritance)
- Çok biçimlilik (polimorfizm)
- Kompozisyon (composition)
- Konsol tabanlı oyun döngüsü ve hata yönetimi

---

## Özellikler

- Konsol tabanlı metin arayüzü
- En az 5 adet oda:
  - Salon (başlangıç)
  - Silah Odası
  - Koridor
  - Depo
  - Şifa Odası
- Eşya sistemi (Item hiyerarşisi):
  - `KeyItem` – Kapı kilidi açan anahtar
  - `PotionItem` – HP yenileyen iksir
  - `WeaponItem` – Saldırı gücünü artıran silah
- Oyuncu sistemi:
  - Bulunduğu oda, HP, saldırı gücü, envanter
- NPC sistemi:
  - `FriendlyNPC` – Seçenekli diyaloglar (muhafız)
  - `EnemyNPC` – Basit savaş sistemi (Sümüksü Yaratık)
- Diyalog ağacı:
  - `ConversationNode` ve `ConversationChoice` ile dallanan konuşmalar
- Hata yönetimi:
  - Geçersiz komut, yanlış yön, olmayan eşya/NPC, envanterde olmayan eşya gibi durumlarda açıklayıcı mesajlar

---

## Kullanılan Teknolojiler

- **Dil:** Java
- **Sürüm:** Java 17 (JDK 17 ile test edildi)
- **Çalışma Ortamı:** Konsol (Eclipse / IntelliJ / VS Code fark etmeksizin çalışır)

---

## Proje Yapısı

Kaynak kodlar `src/adventure` paketinde yer almaktadır:

```text
src/
└── adventure/
    ├── App.java
    ├── GameEngine.java
    ├── Room.java
    ├── Player.java
    ├── Item.java
    ├── KeyItem.java
    ├── PotionItem.java
    ├── WeaponItem.java
    ├── NPC.java
    ├── FriendlyNPC.java
    ├── EnemyNPC.java
    ├── ConversationNode.java
    └── ConversationChoice.java
