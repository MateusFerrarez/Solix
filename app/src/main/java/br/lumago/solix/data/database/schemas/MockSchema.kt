package br.lumago.solix.data.database.schemas

class MockSchema {
    companion object{
        const val INSERT_GROUP = """INSERT INTO Groups(partner_id, created_at)
                VALUES ('12038X98102JX1092NONJKQNW8', '2025-09-01T06:00:00:0000');
        """

        const val INSERT_ENTERPRISE = """INSERT INTO Enterprises (group_id, partner_id, created_at)
            VALUES (1, '11', '2025-09-01T06:00:00:0000');
        """
    }
}