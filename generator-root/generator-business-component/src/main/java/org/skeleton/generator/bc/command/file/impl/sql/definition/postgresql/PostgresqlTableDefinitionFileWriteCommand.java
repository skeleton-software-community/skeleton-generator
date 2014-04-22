package org.skeleton.generator.bc.command.file.impl.sql.definition.postgresql;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skeleton.generator.bc.command.file.impl.sql.SqlFileWriteCommand;
import org.skeleton.generator.model.metadata.DataType;
import org.skeleton.generator.model.om.Column;
import org.skeleton.generator.model.om.Table;

public class PostgresqlTableDefinitionFileWriteCommand extends SqlFileWriteCommand {

	private Table table;
	private Map<String, String> fieldMap;

	/*
	 * constructor
	 */
	public PostgresqlTableDefinitionFileWriteCommand(Table table) {

		super(table.myPackage.model.project.sourceFolder + "\\SQL\\" + table.myPackage.name.toUpperCase(), table.originalName);

		this.table = table;

		fieldMap = new HashMap<>();

		for (int i = 0; i < table.getInsertColumnList().size(); i++) {
			fieldMap.put(table.getInsertColumnList().get(i).name, "ARG" + i);
		}

		for (int i = 0; i < table.columns.size(); i++) {
			if (table.columns.get(i).referenceTable != null) {
				fieldMap.put(table.columns.get(i).name, "ID_ARG" + i);
			}
		}
	}

	@Override
	public void writeContent() throws IOException {
		createTable();
		
		if (table.myPackage.model.project.audited) {
			createAuditTable();
		}
		
		createFind();
		createInsert();
		createUpdate();
		createDelete();

		writeNotOverridableContent();

		skipLine();
	}
	
	
	/*
	 * create table
	 */
	private void createTable()
    {
        writeLine("-- table centrale des elements --");
        writeLine("CREATE TABLE " + table.name);
        writeLine("(");
        write(table.columns.get(0).name + " BIGSERIAL PRIMARY KEY");

        for (int i = 1;i<this.table.columns.size();i++)
        {
            writeLine(",");
            
            write(this.table.columns.get(i).name + " " + DataType.getPostgresqlType(table.columns.get(i).dataType));
            if (this.table.columns.get(i).nullable)
            {
                write(" NULL");
            }
            else
            {
                write(" NOT NULL");
            }
            if (this.table.columns.get(i).unique)
            {
                write(" UNIQUE");
            }
            if (this.table.columns.get(i).referenceTable != null)
            {
                write(" REFERENCES " + table.columns.get(i).referenceTable.name);
                if (this.table.columns.get(i).deleteCascade)
                {
                    write(" ON DELETE CASCADE");
                }
            }
        }

        writeLine(",");
        
        write("UNIQUE (" + this.table.columns.get(1).name);

        for (int i=2;i<=this.table.cardinality;i++)
        {
            write("," + this.table.columns.get(i).name);
        }
        writeLine(")");
        writeLine(")");
        writeLine(";");
        skipLine();

        for (int i = 1; i < this.table.columns.size(); i++)
        {
            if (this.table.columns.get(i).referenceTable != null)
            {
                writeLine("CREATE INDEX ON " + this.table.name + "(" + this.table.columns.get(i).name + ");");
                
            }
        }

        skipLine();
    }
	
	
	/*
	 * create audit table
	 */
	private void createAuditTable()
    {
        writeLine("-- table d'audit des elements --");
        writeLine("CREATE TABLE " + table.name + "_aud");
        writeLine("(");
        writeLine(table.columns.get(0).name + " integer NOT NULL,");
        writeLine("rev integer NOT NULL,");
        writeLine("revtype smallint NOT NULL,");

        for (int i = 1;i<this.table.columns.size();i++)
        {
            writeLine(this.table.columns.get(i).name + " " + DataType.getPostgresqlType(table.columns.get(i).dataType) + " NULL,");
        }

        writeLine("CONSTRAINT " + table.name + "_aud_pkey PRIMARY KEY (id, rev),");
        writeLine("CONSTRAINT " + table.name + "_aud_rev FOREIGN KEY (rev)");
        writeLine("REFERENCES auditentity (id) MATCH SIMPLE");
        writeLine("ON UPDATE NO ACTION ON DELETE NO ACTION");
        writeLine(")");
        writeLine(";");
        skipLine();
    }
	

    /*
     *  create find function 
     */
    private void createFind()
    {
        
        List<Column> findColumnList = this.table.getFindColumnList();
        List<Column> tempColumnList;
        
        writeLine("-- fonction permettant de trouver un element à partir de codes --");
        writeLine("CREATE OR REPLACE FUNCTION find_" + table.name.toLowerCase());
        writeLine("(");

        for (int i = 0;i<findColumnList.size();i++)
        {
            writeLine("v" + findColumnList.get(i).name + " " + DataType.getPostgresqlType(findColumnList.get(i).dataType) + ",");
            
        }

        writeLine("OUT vID BIGINT");
        
        writeLine(")");
        
        writeLine("AS '");
        
        writeLine("DECLARE");
        

        for (int i=1;i<=this.table.cardinality;i++)
        {
            if (this.table.columns.get(i).referenceTable!= null)
            {
                writeLine("v" + this.table.columns.get(i).name + " BIGINT;");
                
            }
        }
        writeLine("BEGIN");
        

        writeLine("IF v" + findColumnList.get(0).name + " IS NULL");
        

        for (int i = 1;i<findColumnList.size();i++)
        {
            writeLine("AND v" + findColumnList.get(i).name + " IS NULL");
            
        }
        writeLine("THEN");
        
        writeLine("vID = NULL;");
        
        writeLine("ELSE");
        

        for (int i= 1;i<=this.table.cardinality;i++)
        {
            if (this.table.columns.get(i).referenceTable != null)
            {
                write("SELECT find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

                tempColumnList = this.table.columns.get(i).referenceTable.getFindColumnList();

                for (int j = 0; j < tempColumnList.size(); j++)
                {
                    write("v" + this.table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name + ",");
                }

                writeLine("v" + this.table.columns.get(i).name + ");");
                
            }
        }

        writeLine("SELECT " + table.name + ".ID");
        
        writeLine("INTO vID"); 
        writeLine("FROM " + table.name);
        writeLine("WHERE " + table.name + "." + this.table.columns.get(1).name + " = v" + this.table.columns.get(1).name);

        for (int i= 2;i<=this.table.cardinality;i++){
            
            writeLine("AND " + table.name + "." + this.table.columns.get(i).name + " = v" + this.table.columns.get(i).name);
        }

        
        writeLine(";");
        
        writeLine("IF vID IS NULL");
        writeLine("THEN");
        writeLine("vID = -1;");
        writeLine("END IF;");
        writeLine("END IF;");
        writeLine("END;");
        writeLine("' LANGUAGE plpgsql;");
        writeLine(";");
        skipLine();
    }

   

    /* 
     * create insert functions
     */
    private void createInsert()
    {
        List<Column> insertColumnList = this.table.getInsertColumnList();
        List<Column> tempColumnList;

        writeLine("-- fonction permettant d'insérer un nouvel element --");
        skipLine();
        writeLine("CREATE OR REPLACE FUNCTION insert_" + table.name.toLowerCase());
        
        writeLine("(");
        

        write("v" + table.columns.get(1).name + " " + DataType.getPostgresqlType(table.columns.get(1).dataType));

        for (int i= 2;i<table.columns.size();i++)
        {
            writeLine(","); 
            write("v" + table.columns.get(i).name + " " + DataType.getPostgresqlType(table.columns.get(i).dataType));
        }

        
        writeLine(")");
        writeLine("RETURNS void AS '");
        writeLine("BEGIN");

        write("INSERT INTO " + table.name + " (" + this.table.columns.get(1).name);

        for (int i = 2; i < this.table.columns.size(); i++)
        {
            write(", " + this.table.columns.get(i).name);
        }

        write(") VALUES (v" + this.table.columns.get(1).name);

        for (int i= 2;i<this.table.columns.size();i++)
        {
            write(", v" + this.table.columns.get(i).name);
        }

        writeLine(");");
        writeLine("END;");
        writeLine("' LANGUAGE plpgsql;");
        skipLine();


        writeLine("-- fonction permettant d'insérer un nouvel element à l'aide des codes --");
        writeLine("CREATE OR REPLACE FUNCTION insert_" + table.name.toLowerCase() + "_by_code");
        
        writeLine("(");

        write("v" + insertColumnList.get(0).name + " " + DataType.getPostgresqlType(insertColumnList.get(0).dataType));

        for (int i= 1;i<insertColumnList.size();i++)
        {
            writeLine(",");
            write("v" + insertColumnList.get(i).name + " " + DataType.getPostgresqlType(insertColumnList.get(i).dataType));
        }

        
        writeLine(")");
        writeLine("RETURNS void AS '");
        writeLine("DECLARE");
        

        for (int i= 1;i<this.table.columns.size();i++)
        {
            if (this.table.columns.get(i).referenceTable != null)
            {
                writeLine("v" + this.table.columns.get(i).name + " BIGINT;");
                
            }
        }
        writeLine("BEGIN");
        

        for (int i= 1;i<this.table.columns.size();i++)
        {
            if (this.table.columns.get(i).referenceTable != null)
            {
                write("SELECT find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

                tempColumnList = this.table.columns.get(i).referenceTable.getFindColumnList();
                boolean begin = true;

                for (int j=0;j<tempColumnList.size();j++)
                {
                    if (begin)
                    {

                        write("v" + this.table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name);
                        begin = false;
                    }
                    else
                    {
                        write(", v" + this.table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name);
                    }
                }

                writeLine(") into v" + this.table.columns.get(i).name + ";");
                
            }
        }

        write("PERFORM insert_" + table.name.toLowerCase() + " (v" + this.table.columns.get(1).name);

        for (int i = 2; i < this.table.columns.size(); i++)
        {
            write(", v" + this.table.columns.get(i).name);
        }

        writeLine(");");
        
        writeLine("END;");
        
        writeLine("' LANGUAGE plpgsql;");
        skipLine();
    }

    /* create update functions */

    private void createUpdate()
    {
        List<Column> findColumnList = this.table.getFindColumnList();
        List<Column> insertColumnList = this.table.getInsertColumnList();
        List<Column> tempColumnList;

        writeLine("-- fonction permettant de mettre à jour un element à l'aide des codes --");
        writeLine("CREATE OR REPLACE FUNCTION update_" + table.name.toLowerCase() + "_by_code");
        
        write("(");

        write("v" + insertColumnList.get(0).name + " " + DataType.getPostgresqlType(insertColumnList.get(0).dataType));

        for (int i= 1;i<insertColumnList.size();i++)
        {
            write(",");
            
            write("v" + insertColumnList.get(i).name + " " + DataType.getPostgresqlType(insertColumnList.get(i).dataType));
        }

        
        writeLine(")");
        writeLine("RETURNS void AS '");
        writeLine("DECLARE");
        

        for (int i= 1;i<this.table.columns.size();i++)
        {
            if (this.table.columns.get(i).referenceTable != null)
            {
                writeLine("v" + this.table.columns.get(i).name + " BIGINT;");
                
            }
        }

        writeLine("vID BIGINT;");
        
        writeLine("BEGIN");
        

        boolean begin = true;
        for (int i= 1;i<this.table.columns.size();i++)
        {
            if (this.table.columns.get(i).referenceTable != null)
            {
                write("SELECT find_" + this.table.columns.get(i).referenceTable.name.toLowerCase() + " (");

                tempColumnList = this.table.columns.get(i).referenceTable.getFindColumnList();
                begin = true;

                for (int j=0;j<tempColumnList.size();j++)
                {
                    if (begin)
                    {

                        write("v" + this.table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name);
                        begin = false;
                    }
                    else
                    {
                        writeLine(", v" + this.table.columns.get(i).name.replace("_ID", "_") + tempColumnList.get(j).name);
                    }
                }

                writeLine(") into v" + this.table.columns.get(i).name + ";");
                
            }
        }

        begin = true;
        write("SELECT find_" + this.table.name.toLowerCase() + " (");
        
        for (int i= 0;i<findColumnList.size();i++)
        {
            if (begin)
            {
                write("v" + findColumnList.get(i).name);
                begin = false;
            }
            else
            {
                write(", v" + findColumnList.get(i).name);
            }
        }
        writeLine(") into vID;");
        

        writeLine("UPDATE " + table.name + " SET " + this.table.columns.get(1).name + " = v" + this.table.columns.get(1).name);

        for (int i= 2;i<this.table.columns.size();i++)
        {
            writeLine(",");
            
            writeLine(this.table.columns.get(i).name + " = v" + this.table.columns.get(i).name);
        }

        
        writeLine("WHERE " + table.name + ".ID = vID;");
        
        writeLine("END;");
        
        writeLine("' LANGUAGE plpgsql;");
        skipLine();
    }

    /* create delete functions */

    private void createDelete()
    {
        List<Column> findColumnList = this.table.getFindColumnList();

        writeLine("-- fonction permettant d'effacer un element à l'aide des codes --");
        writeLine("CREATE OR REPLACE FUNCTION delete_" + table.name.toLowerCase() + "_by_code");
        
        write("(");

        write("v" + findColumnList.get(0).name + " " + DataType.getPostgresqlType(findColumnList.get(0).dataType));

        for (int i= 1;i<findColumnList.size();i++)
        {
            write(",");
            
            write("v" + findColumnList.get(i).name + " " + DataType.getPostgresqlType(findColumnList.get(i).dataType));
        }

        
        writeLine(")");
        
        writeLine("RETURNS VOID AS '");
        
        writeLine("DECLARE");
        

        writeLine("vID BIGINT;");
        
        writeLine("BEGIN");
        
   
        write("SELECT find_" + this.table.name.toLowerCase() + " (");
        boolean begin = true;
        for (int i= 0;i<findColumnList.size();i++)
        {
            if (begin)
            {
                write("v" + findColumnList.get(i).name);
                begin = false;
            }
            else
            {
                write(", v" + findColumnList.get(i).name);
            }
        }
        writeLine(") into vID;");
        
        writeLine("DELETE FROM " + table.name + " WHERE ID = vID;");
        
        writeLine("END;");
        
        writeLine("' LANGUAGE plpgsql;");
        skipLine();
    }

}
