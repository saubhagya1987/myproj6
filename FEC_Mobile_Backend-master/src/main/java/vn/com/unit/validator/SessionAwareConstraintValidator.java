package vn.com.unit.validator;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
  
public abstract class SessionAwareConstraintValidator<T> { 
	@Autowired
    private SessionFactory sessionFactoryBase;  
    boolean openedNewTransaction;  
    private Session tmpSession;  
  
    public SessionAwareConstraintValidator() {  
    }  
    @Transactional(readOnly=true)
    public boolean isValid(T value, ConstraintValidatorContext context) {
    	boolean result=true;
    	if(sessionFactoryBase!=null){
    		
        openTmpSession();
    	
         result = isValidInSession( value, context );  
        
        closeTmpSession();

    	}
        return result;  
    }  
  
    public abstract boolean isValidInSession(T value, ConstraintValidatorContext context);  
  
    public SessionFactory getSessionFactoryBase() {  
        return sessionFactoryBase;  
    }  
    @Autowired
    public void setSessionFactoryBase(SessionFactory sessionFactory) {  
    	this.sessionFactoryBase = sessionFactory;  
    }  
  
    public Session getTmpSession() {  
        return tmpSession;  
    }  
  
    private void openTmpSession() {  
        Session currentSession;  
        try {  
            currentSession = getSessionFactoryBase().getCurrentSession();  
        }  
        catch ( HibernateException e ) {  
            throw new ValidationException( "Unable to determine current Hibernate session", e );  
        }  
        if ( !currentSession.getTransaction().isActive() ) {  
            currentSession.beginTransaction();  
            openedNewTransaction = true;  
        }  
        try {  
            tmpSession = currentSession.getSessionFactory().openSession();  
        }  
        catch ( HibernateException e ) {  
            throw new ValidationException( "Unable to open temporary session", e );  
        }  
    }  
  
    private void closeTmpSession() {  
        if ( openedNewTransaction ) {  
            try {  
            	getSessionFactoryBase().getCurrentSession().getTransaction().commit();  
            }  
            catch ( HibernateException e ) {  
                throw new ValidationException( "Unable to commit transaction for temporary session", e );  
            }  
        }  
        try {  
            tmpSession.close();  
        }  
        catch ( HibernateException e ) {  
            throw new ValidationException( "Unable to close temporary Hibernate session", e );  
        }  
    }  
}  
 
