package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
/*
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
*/
/*
  def index = Action {
    Ok("Hello World")
  }
*/

  def index = Action {
    Redirect(routes.Application.retrieveTasks)
  }

  val taskForm = Form("description" -> nonEmptyText)
  def createTask = Action { 
    implicit request => taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(models.Task.retrieveAll(), errors)), 
      description => {
	models.Task.create(description)
	Redirect(routes.Application.retrieveTasks) 
      }
    ) 
  }

  def retrieveTasks = Action {
    Ok(views.html.index(models.Task.retrieveAll(), taskForm))
  }

  def retrieveTask(id: Long) = TODO

  def deleteTask(id: Long) = Action { 
    models.Task.delete(id) 
    Redirect(routes.Application.retrieveTasks)
  }
}
