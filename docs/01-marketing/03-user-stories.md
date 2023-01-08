# Пользовательские истории

## 

Title: Returns and exchanges go to inventory.
Story
-----
As a store owner,
I want to add items back to inventory when they are returned or exchanged,
so that I can track inventory.
Acceptance Criteria
--------------------
Scenario 1: Items returned for refund should be added to inventory.
Given that a customer previously bought a black sweater from me and I have three black sweaters in inventory,
when they return the black sweater for a refund,
then I should have four black sweaters in inventory.
Scenario 2: Exchanged items should be returned to inventory.
Given that a customer previously bought a blue garment from me and I have two blue garments in inventory and three black garments in inventory,
when they exchange the blue garment for a black garment,
then I should have three blue garments in inventory and two black garments in inventory.
Stretch Goals
--------------
- How can we automate the exchange or return process?
  Notes
-----
- We currently process returns by ...
- We currently process exchanges by ...
  Priority: Medium
  Story Points: 8
