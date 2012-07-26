package models

import anorm._
import anorm.SqlParser._

import play.api.db._
import play.api.Play.current

case class Task(id: Long, description: String)

object Task {
  val mapper = { 
    get[Long]("id") ~ 
    get[String]("description") map {
      case id~description => Task(id, description) 
    }
  }

  def create(description: String) { 
    DB.withConnection { 
      implicit c => SQL("insert into task (description) values ({description})").on('description -> description).executeUpdate() 
    }
  }

  def retrieveAll(): List[Task] = {
    DB.withConnection {
      implicit c => SQL("select * from task").as(mapper *)
    }
  }

  def retrieve(id: Long) {}

  def delete(id: Long) { 
    DB.withConnection { 
      implicit c => SQL("delete from task where id = {id}").on('id -> id).executeUpdate() 
    }		    
  }
}
