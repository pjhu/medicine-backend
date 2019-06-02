ALTER TABLE "order" RENAME COLUMN shortDescription TO quantity;
ALTER TABLE "order" RENAME COLUMN longDescription TO total_Price;
ALTER TABLE "order" RENAME TO user_order;