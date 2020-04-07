INSERT INTO `demo_eav`.`object_type` (`object_type_id`, `name`, `parent_id`) VALUES ('4', 'Adjustment', '1');

INSERT INTO `demo_eav`.`attribute` (`attribute_id`, `attr_type`, `name`) VALUES ('14', '0', 'Adjustment Status');
INSERT INTO `demo_eav`.`attribute` (`attribute_id`, `attr_type`, `name`) VALUES ('15', '0', 'Approved Date');
INSERT INTO `demo_eav`.`attribute` (`attribute_id`, `attr_type`, `name`) VALUES ('16', '0', 'Rejected Reason');

INSERT INTO `demo_eav`.`attr_object_type` (`object_type_id`, `attr_id` ) VALUES ('4','14');
INSERT INTO `demo_eav`.`attr_object_type` (`object_type_id`, `attr_id` ) VALUES ('4','15');
INSERT INTO `demo_eav`.`attr_object_type` (`object_type_id`, `attr_id`) VALUES ('4','16');